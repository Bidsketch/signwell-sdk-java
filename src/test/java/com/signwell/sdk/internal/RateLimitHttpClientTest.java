package com.signwell.sdk.internal;

import static org.junit.jupiter.api.Assertions.*;

import java.net.http.HttpHeaders;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class RateLimitHttpClientTest {
    @Test
    void retryDelaysHonorSecondsDatesAndTheMaximumWait() {
        Instant now = Instant.parse("2026-10-12T00:00:00Z");
        assertEquals(
                Duration.ofSeconds(1), RateLimitHttpClient.retryDelay(headers(Map.of()), 0, now));
        assertEquals(
                Duration.ofSeconds(2), RateLimitHttpClient.retryDelay(headers(Map.of()), 1, now));
        assertEquals(
                Duration.ofSeconds(7),
                RateLimitHttpClient.retryDelay(
                        headers(Map.of("Retry-After", List.of("7"))), 0, now));
        assertEquals(
                Duration.ofSeconds(7),
                RateLimitHttpClient.retryDelay(
                        headers(Map.of("Retry-After", List.of("Mon, 12 Oct 2026 00:00:07 GMT"))),
                        0,
                        now));
        assertEquals(
                Duration.ofSeconds(1),
                RateLimitHttpClient.retryDelay(
                        headers(Map.of("Retry-After", List.of("invalid"))), 0, now));
        assertEquals(
                Duration.ofSeconds(1),
                RateLimitHttpClient.retryDelay(
                        headers(Map.of("Retry-After", List.of("-5"))), 0, now));
        assertNull(
                RateLimitHttpClient.retryDelay(
                        headers(Map.of("Retry-After", List.of("120"))), 0, now));
    }

    private static HttpHeaders headers(Map<String, List<String>> values) {
        return HttpHeaders.of(values, (name, value) -> true);
    }
}
