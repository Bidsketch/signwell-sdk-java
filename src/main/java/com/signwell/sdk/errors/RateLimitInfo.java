package com.signwell.sdk.errors;

import java.net.http.HttpHeaders;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/** Rate-limit metadata parsed from response headers. */
public final class RateLimitInfo {
    private final Long limit;
    private final Long remaining;
    private final Long reset;
    private final Instant resetTime;
    private final Duration retryAfter;
    private final Instant retryTime;

    private RateLimitInfo(
            Long limit, Long remaining, Long reset, Duration retryAfter, Instant retryTime) {
        this.limit = limit;
        this.remaining = remaining;
        this.reset = reset;
        this.resetTime = epoch(reset);
        this.retryAfter = retryAfter;
        this.retryTime =
                retryTime != null
                        ? retryTime
                        : retryAfter == null ? null : Instant.now().plus(retryAfter);
    }

    public static RateLimitInfo from(HttpHeaders headers) {
        if (headers == null) return new RateLimitInfo(null, null, null, null, null);
        Retry retry = retry(headers);
        return new RateLimitInfo(
                number(headers, "ratelimit-limit", "x-ratelimit-limit"),
                number(headers, "ratelimit-remaining", "x-ratelimit-remaining"),
                number(headers, "ratelimit-reset", "x-ratelimit-reset"),
                retry.duration,
                retry.time);
    }

    private static Long number(HttpHeaders headers, String... names) {
        for (String name : names) {
            String value = headers.firstValue(name).orElse(null);
            if (value == null) continue;
            try {
                return Long.valueOf(value.trim());
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
        return null;
    }

    private static Retry retry(HttpHeaders headers) {
        String value = headers.firstValue("retry-after").orElse(null);
        if (value == null) return new Retry(null, null);
        try {
            long seconds = Long.parseLong(value.trim());
            return seconds < 0
                    ? new Retry(null, null)
                    : new Retry(Duration.ofSeconds(seconds), null);
        } catch (NumberFormatException ignored) {
            try {
                return new Retry(
                        null,
                        ZonedDateTime.parse(value.trim(), DateTimeFormatter.RFC_1123_DATE_TIME)
                                .toInstant());
            } catch (RuntimeException invalidDate) {
                return new Retry(null, null);
            }
        }
    }

    private static Instant epoch(Long value) {
        if (value == null) return null;
        try {
            return Instant.ofEpochSecond(value);
        } catch (RuntimeException ignored) {
            return null;
        }
    }

    public Long getLimit() {
        return limit;
    }

    public Long getRemaining() {
        return remaining;
    }

    public Long getReset() {
        return reset;
    }

    public Instant getResetTime() {
        return resetTime;
    }

    public Duration getRetryAfter() {
        return retryAfter;
    }

    public Instant getRetryTime() {
        return retryTime;
    }

    private static final class Retry {
        private final Duration duration;
        private final Instant time;

        private Retry(Duration duration, Instant time) {
            this.duration = duration;
            this.time = time;
        }
    }
}
