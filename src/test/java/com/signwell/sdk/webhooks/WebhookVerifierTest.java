package com.signwell.sdk.webhooks;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.signwell.sdk.errors.WebhookReplayCapacityException;
import com.signwell.sdk.errors.WebhookVerificationException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.Test;

class WebhookVerifierTest {
    private static final ObjectMapper JSON = new ObjectMapper();
    private static final String WEBHOOK_ID = "webhook-secret";

    @Test
    void verifiesShapeSignatureFreshnessAndCanonicalReplayKeys() {
        Instant now = Instant.ofEpochSecond(1_000).plusMillis(900);
        JsonNode event = event("document_completed", "995");
        assertTrue(WebhookVerifier.verify(event, WEBHOOK_ID, Duration.ofSeconds(5), now));
        assertDoesNotThrow(
                () -> WebhookVerifier.verifyOrThrow(event, WEBHOOK_ID, Duration.ofSeconds(5), now));

        JsonNode uppercase = event.deepCopy();
        ((com.fasterxml.jackson.databind.node.ObjectNode) uppercase)
                .put("hash", event.get("hash").asText().toUpperCase(Locale.ROOT));
        assertTrue(WebhookVerifier.verify(uppercase, WEBHOOK_ID, Duration.ofSeconds(5), now));
        assertEquals(WebhookVerifier.replayKey(event), WebhookVerifier.replayKey(uppercase));

        assertFalse(
                WebhookVerifier.verify(
                        event("document_completed", "994"),
                        WEBHOOK_ID,
                        Duration.ofSeconds(5),
                        now));
        assertFalse(WebhookVerifier.verify(JSON.createObjectNode(), WEBHOOK_ID));
        assertFalse(WebhookVerifier.verify(event, "   "));
        assertFalse(
                WebhookVerifier.verify(
                        JSON.createObjectNode().put("type", "x").put("time", "1").put("hash", 3),
                        WEBHOOK_ID));
        assertThrows(
                WebhookVerificationException.class,
                () ->
                        WebhookVerifier.verifyOrThrow(
                                event, WEBHOOK_ID, Duration.ofSeconds(-1), now));
    }

    @Test
    void invalidSignaturesAreNeverStoredAndConcurrentReplaysAcceptOnlyOnce() {
        AtomicInteger writes = new AtomicInteger();
        WebhookReplayStore counting =
                (key, expiry) -> {
                    writes.incrementAndGet();
                    return CompletableFuture.completedFuture(true);
                };
        JsonNode invalid = event("document_completed", "1000").deepCopy();
        ((com.fasterxml.jackson.databind.node.ObjectNode) invalid).put("hash", "invalid");
        assertFalse(
                WebhookVerifier.verifyOnce(
                                invalid,
                                WEBHOOK_ID,
                                counting,
                                Duration.ofMinutes(5),
                                Instant.ofEpochSecond(1_000))
                        .join());
        assertEquals(0, writes.get());

        InMemoryWebhookReplayStore store =
                new InMemoryWebhookReplayStore(100, () -> Instant.ofEpochSecond(1_000));
        JsonNode valid = event("document_completed", "1000");
        List<CompletableFuture<Boolean>> attempts = new ArrayList<>();
        for (int index = 0; index < 64; index++) {
            attempts.add(
                    CompletableFuture.supplyAsync(
                                    () ->
                                            WebhookVerifier.verifyOnce(
                                                    valid,
                                                    WEBHOOK_ID,
                                                    store,
                                                    Duration.ofMinutes(5),
                                                    Instant.ofEpochSecond(1_000)))
                            .thenCompose(value -> value));
        }
        assertEquals(
                1,
                attempts.stream()
                        .map(CompletableFuture::join)
                        .filter(Boolean::booleanValue)
                        .count());
    }

    @Test
    void replayWindowCoversFractionalAndFutureBoundariesAndCapacityFailsClosed() {
        AtomicReference<Instant> clock =
                new AtomicReference<>(Instant.ofEpochSecond(1_000).plusMillis(900));
        InMemoryWebhookReplayStore store = new InMemoryWebhookReplayStore(2, clock::get);
        JsonNode boundary = event("document_completed", "995");
        assertTrue(
                WebhookVerifier.verifyOnce(
                                boundary, WEBHOOK_ID, store, Duration.ofSeconds(5), clock.get())
                        .join());
        assertFalse(
                WebhookVerifier.verifyOnce(
                                boundary, WEBHOOK_ID, store, Duration.ofSeconds(5), clock.get())
                        .join());
        clock.set(Instant.ofEpochSecond(1_001));
        assertTrue(
                WebhookVerifier.verifyOnce(
                                boundary, WEBHOOK_ID, store, Duration.ofSeconds(6), clock.get())
                        .join());

        AtomicReference<Instant> futureClock = new AtomicReference<>(Instant.ofEpochSecond(2_000));
        InMemoryWebhookReplayStore futureStore =
                new InMemoryWebhookReplayStore(2, futureClock::get);
        JsonNode future = event("document_completed", "2005");
        assertTrue(
                WebhookVerifier.verifyOnce(
                                future,
                                WEBHOOK_ID,
                                futureStore,
                                Duration.ofSeconds(5),
                                futureClock.get())
                        .join());
        futureClock.set(Instant.ofEpochSecond(2_010));
        assertFalse(
                WebhookVerifier.verifyOnce(
                                future,
                                WEBHOOK_ID,
                                futureStore,
                                Duration.ofSeconds(5),
                                futureClock.get())
                        .join());
        futureClock.set(Instant.ofEpochSecond(2_011));
        assertTrue(
                WebhookVerifier.verifyOnce(
                                future,
                                WEBHOOK_ID,
                                futureStore,
                                Duration.ofSeconds(6),
                                futureClock.get())
                        .join());

        InMemoryWebhookReplayStore capacity =
                new InMemoryWebhookReplayStore(1, () -> Instant.ofEpochSecond(3_000));
        assertTrue(
                WebhookVerifier.verifyOnce(
                                event("one", "3000"),
                                WEBHOOK_ID,
                                capacity,
                                Duration.ofMinutes(5),
                                Instant.ofEpochSecond(3_000))
                        .join());
        CompletionException full =
                assertThrows(
                        CompletionException.class,
                        () ->
                                WebhookVerifier.verifyOnce(
                                                event("two", "3000"),
                                                WEBHOOK_ID,
                                                capacity,
                                                Duration.ofMinutes(5),
                                                Instant.ofEpochSecond(3_000))
                                        .join());
        assertInstanceOf(WebhookReplayCapacityException.class, full.getCause());
    }

    @Test
    void throwingApisRequireFreshnessForReplayAndRejectDuplicates() {
        JsonNode event = event("document_completed", "4000");
        InMemoryWebhookReplayStore store =
                new InMemoryWebhookReplayStore(10, () -> Instant.ofEpochSecond(4_000));
        assertThrows(
                WebhookVerificationException.class,
                () ->
                        WebhookVerifier.verifyOnceOrThrow(
                                event, WEBHOOK_ID, store, null, Instant.ofEpochSecond(4_000)));
        WebhookVerifier.verifyOnceOrThrow(
                        event,
                        WEBHOOK_ID,
                        store,
                        Duration.ofMinutes(5),
                        Instant.ofEpochSecond(4_000))
                .join();
        CompletionException duplicate =
                assertThrows(
                        CompletionException.class,
                        () ->
                                WebhookVerifier.verifyOnceOrThrow(
                                                event,
                                                WEBHOOK_ID,
                                                store,
                                                Duration.ofMinutes(5),
                                                Instant.ofEpochSecond(4_000))
                                        .join());
        assertInstanceOf(WebhookVerificationException.class, duplicate.getCause());
    }

    private static JsonNode event(String type, String time) {
        String hash = hmac(WEBHOOK_ID, type + "@" + time);
        return JSON.createObjectNode().put("type", type).put("time", time).put("hash", hash);
    }

    private static String hmac(String key, String value) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            StringBuilder result = new StringBuilder();
            for (byte item : mac.doFinal(value.getBytes(StandardCharsets.UTF_8))) {
                result.append(String.format(Locale.ROOT, "%02x", item & 0xff));
            }
            return result.toString();
        } catch (Exception failure) {
            throw new IllegalStateException(failure);
        }
    }
}
