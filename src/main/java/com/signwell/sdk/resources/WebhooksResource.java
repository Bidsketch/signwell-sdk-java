package com.signwell.sdk.resources;

import com.signwell.sdk.internal.SdkFutures;
import com.signwell.sdk.models.CreateWebhookRequest;
import com.signwell.sdk.models.WebhookResponse;
import com.signwell.sdk.raw.WebhooksApi;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class WebhooksResource {
    private final WebhooksApi raw;

    public WebhooksResource(WebhooksApi raw) {
        this.raw = raw;
    }

    public WebhooksApi raw() {
        return raw;
    }

    public CompletableFuture<WebhookResponse> create(CreateWebhookRequest request) {
        return SdkFutures.callRequired(
                () -> raw.createWebhook(Objects.requireNonNull(request, "request")),
                "SignWell returned an empty webhook response.");
    }

    public CompletableFuture<List<WebhookResponse>> list() {
        return SdkFutures.call(raw::listWebhooks)
                .thenApply(value -> value == null ? List.of() : value);
    }

    public CompletableFuture<Void> delete(UUID id) {
        return SdkFutures.call(() -> raw.deleteWebhook(Objects.requireNonNull(id, "id")));
    }
}
