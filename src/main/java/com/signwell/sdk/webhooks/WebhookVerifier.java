package com.signwell.sdk.webhooks;

import com.fasterxml.jackson.databind.JsonNode;
import com.signwell.sdk.errors.WebhookReplayCapacityException;
import com.signwell.sdk.errors.WebhookVerificationException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/** HMAC-SHA256 verification, freshness checks, and replay protection for SignWell webhooks. */
public final class WebhookVerifier {
    private WebhookVerifier() {}

    public static boolean verify(JsonNode event, String webhookId) {
        return verify(event, webhookId, null, Instant.now());
    }

    public static boolean verify(
            JsonNode event, String webhookId, Duration freshness, Instant now) {
        try {
            verifyOrThrow(event, webhookId, freshness, now);
            return true;
        } catch (WebhookVerificationException ignored) {
            return false;
        }
    }

    public static void verifyOrThrow(JsonNode event, String webhookId) {
        verifyOrThrow(event, webhookId, null, Instant.now());
    }

    public static void verifyOrThrow(
            JsonNode event, String webhookId, Duration freshness, Instant now) {
        verifyData(event, webhookId, freshness, Objects.requireNonNull(now, "now"));
    }

    public static CompletableFuture<Boolean> verifyOnce(
            JsonNode event, String webhookId, WebhookReplayStore replayStore, Duration freshness) {
        return verifyOnce(event, webhookId, replayStore, freshness, Instant.now());
    }

    public static CompletableFuture<Boolean> verifyOnce(
            JsonNode event,
            String webhookId,
            WebhookReplayStore replayStore,
            Duration freshness,
            Instant now) {
        CompletableFuture<Boolean> result = new CompletableFuture<>();
        CompletableFuture<Void> verification;
        try {
            verification = verifyOnceOrThrow(event, webhookId, replayStore, freshness, now);
        } catch (WebhookReplayCapacityException failure) {
            return CompletableFuture.failedFuture(failure);
        } catch (WebhookVerificationException failure) {
            return CompletableFuture.completedFuture(false);
        } catch (Throwable failure) {
            return CompletableFuture.failedFuture(failure);
        }
        verification.whenComplete(
                (ignored, failure) -> {
                    Throwable cause = unwrap(failure);
                    if (cause == null) result.complete(true);
                    else if (cause instanceof WebhookReplayCapacityException)
                        result.completeExceptionally(cause);
                    else if (cause instanceof WebhookVerificationException) result.complete(false);
                    else result.completeExceptionally(cause);
                });
        return result;
    }

    public static CompletableFuture<Void> verifyOnceOrThrow(
            JsonNode event, String webhookId, WebhookReplayStore replayStore, Duration freshness) {
        return verifyOnceOrThrow(event, webhookId, replayStore, freshness, Instant.now());
    }

    public static CompletableFuture<Void> verifyOnceOrThrow(
            JsonNode event,
            String webhookId,
            WebhookReplayStore replayStore,
            Duration freshness,
            Instant now) {
        Objects.requireNonNull(replayStore, "replayStore");
        if (freshness == null) {
            throw new WebhookVerificationException(
                    "Freshness is required when replay protection is enabled.");
        }
        ParsedEvent parsed = verifyData(event, webhookId, freshness, now);
        CompletableFuture<Boolean> added;
        try {
            added =
                    replayStore
                            .tryAdd(
                                    parsed.replayKey(),
                                    replayExpiration(parsed.timestamp, freshness))
                            .toCompletableFuture();
        } catch (Throwable failure) {
            return CompletableFuture.failedFuture(failure);
        }
        return added.thenCompose(
                accepted ->
                        accepted
                                ? CompletableFuture.completedFuture(null)
                                : CompletableFuture.failedFuture(
                                        new WebhookVerificationException(
                                                "Webhook event has already been processed.")));
    }

    public static String replayKey(JsonNode event) {
        return parse(event).replayKey();
    }

    private static ParsedEvent verifyData(
            JsonNode event, String webhookId, Duration freshness, Instant now) {
        if (webhookId == null || webhookId.trim().isEmpty()) {
            throw new WebhookVerificationException("Webhook ID must be a non-empty string.");
        }
        ParsedEvent parsed = parse(event);
        if (freshness != null) {
            if (freshness.isNegative()) {
                throw new WebhookVerificationException("Freshness must be non-negative.");
            }
            BigDecimal difference =
                    new BigDecimal(BigInteger.valueOf(now.getEpochSecond()))
                            .subtract(new BigDecimal(BigInteger.valueOf(parsed.timestamp)))
                            .abs();
            BigDecimal allowed =
                    BigDecimal.valueOf(freshness.getSeconds())
                            .add(BigDecimal.valueOf(freshness.getNano(), 9));
            if (difference.compareTo(allowed) > 0) {
                throw new WebhookVerificationException(
                        "Webhook timestamp is outside the allowed freshness window.");
            }
        }
        String calculated = hmac(webhookId, parsed.type + "@" + parsed.timeText);
        if (!constantTimeEquals(calculated, parsed.hash)) {
            throw new WebhookVerificationException("Webhook signature is invalid.");
        }
        return parsed;
    }

    private static ParsedEvent parse(JsonNode event) {
        if (event == null || !event.isObject()) {
            throw new WebhookVerificationException("Webhook event must be a JSON object.");
        }
        JsonNode type = event.get("type");
        JsonNode time = event.get("time");
        JsonNode hash = event.get("hash");
        if (type == null || time == null || hash == null) {
            throw new WebhookVerificationException(
                    "Webhook event requires type, time, and hash. Pass the event object, not the"
                            + " full payload.");
        }
        String typeText = scalar(type);
        String timeText = scalar(time);
        String hashText = hash.isTextual() ? hash.textValue() : "";
        if (typeText.isEmpty() || timeText.isEmpty() || hashText.isEmpty()) {
            throw new WebhookVerificationException(
                    "Webhook event type, time, and hash cannot be empty.");
        }
        long timestamp;
        try {
            timestamp = Long.parseLong(timeText);
        } catch (NumberFormatException failure) {
            throw new WebhookVerificationException(
                    "Webhook event time must be a Unix timestamp.", failure);
        }
        return new ParsedEvent(typeText, timeText, hashText.toLowerCase(Locale.ROOT), timestamp);
    }

    private static String scalar(JsonNode value) {
        return value.isTextual() || value.isIntegralNumber() ? value.asText() : "";
    }

    private static String hmac(String key, String value) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] bytes = mac.doFinal(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder(bytes.length * 2);
            for (byte item : bytes) hex.append(String.format(Locale.ROOT, "%02x", item & 0xff));
            return hex.toString();
        } catch (GeneralSecurityException failure) {
            throw new WebhookVerificationException(
                    "Webhook signature verification is unavailable.", failure);
        }
    }

    private static boolean constantTimeEquals(String expected, String supplied) {
        byte[] left = expected.getBytes(StandardCharsets.US_ASCII);
        byte[] right = supplied.toLowerCase(Locale.ROOT).getBytes(StandardCharsets.US_ASCII);
        int length = Math.max(left.length, right.length);
        int difference = left.length ^ right.length;
        for (int index = 0; index < length; index++) {
            difference |=
                    (index < left.length ? left[index] : 0)
                            ^ (index < right.length ? right[index] : 0);
        }
        return difference == 0;
    }

    private static Instant replayExpiration(long timestamp, Duration freshness) {
        try {
            return Instant.ofEpochSecond(timestamp).plus(freshness).plusSeconds(1);
        } catch (DateTimeException | ArithmeticException ignored) {
            return Instant.MAX;
        }
    }

    private static Throwable unwrap(Throwable failure) {
        Throwable current = failure;
        while (current instanceof CompletionException && current.getCause() != null) {
            current = current.getCause();
        }
        return current;
    }

    private static final class ParsedEvent {
        private final String type;
        private final String timeText;
        private final String hash;
        private final long timestamp;

        private ParsedEvent(String type, String timeText, String hash, long timestamp) {
            this.type = type;
            this.timeText = timeText;
            this.hash = hash;
            this.timestamp = timestamp;
        }

        private String replayKey() {
            return "signwell:" + type + ":" + timeText + ":" + hash;
        }
    }
}
