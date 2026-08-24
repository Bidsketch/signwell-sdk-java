package com.signwell.sdk.webhooks;

import java.time.Instant;
import java.util.concurrent.CompletionStage;

/** Atomic replay-store contract. Return false only when the key already exists. */
@FunctionalInterface
public interface WebhookReplayStore {
    CompletionStage<Boolean> tryAdd(String key, Instant expiresAt);
}
