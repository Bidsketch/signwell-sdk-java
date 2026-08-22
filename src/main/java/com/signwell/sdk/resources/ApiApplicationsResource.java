package com.signwell.sdk.resources;

import com.signwell.sdk.internal.SdkFutures;
import com.signwell.sdk.models.ApiApplicationResponse;
import com.signwell.sdk.raw.ApiApplicationApi;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class ApiApplicationsResource {
    private final ApiApplicationApi raw;

    public ApiApplicationsResource(ApiApplicationApi raw) {
        this.raw = raw;
    }

    public ApiApplicationApi raw() {
        return raw;
    }

    public CompletableFuture<ApiApplicationResponse> get(UUID id) {
        return SdkFutures.callRequired(
                () -> raw.getApiApplication(Objects.requireNonNull(id, "id")),
                "SignWell returned an empty API application response.");
    }

    public CompletableFuture<Void> delete(UUID id) {
        return SdkFutures.call(() -> raw.deleteApiApplication(Objects.requireNonNull(id, "id")));
    }
}
