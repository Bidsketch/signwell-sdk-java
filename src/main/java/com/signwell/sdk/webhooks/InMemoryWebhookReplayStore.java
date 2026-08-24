package com.signwell.sdk.webhooks;

import com.signwell.sdk.errors.WebhookReplayCapacityException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/** Thread-safe single-process replay store for development and tests. */
public final class InMemoryWebhookReplayStore implements WebhookReplayStore {
    private final Map<String, Instant> entries = new HashMap<>();
    private final int capacity;
    private final Supplier<Instant> clock;

    public InMemoryWebhookReplayStore() {
        this(10_000, Instant::now);
    }

    public InMemoryWebhookReplayStore(int capacity) {
        this(capacity, Instant::now);
    }

    public InMemoryWebhookReplayStore(int capacity, Supplier<Instant> clock) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive.");
        this.capacity = capacity;
        this.clock = Objects.requireNonNull(clock, "clock");
    }

    @Override
    public synchronized CompletableFuture<Boolean> tryAdd(String key, Instant expiresAt) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(expiresAt, "expiresAt");
        Instant now = clock.get();
        entries.entrySet().removeIf(entry -> !entry.getValue().isAfter(now));
        if (entries.containsKey(key)) return CompletableFuture.completedFuture(false);
        if (entries.size() >= capacity) throw new WebhookReplayCapacityException();
        entries.put(key, expiresAt);
        return CompletableFuture.completedFuture(true);
    }
}
