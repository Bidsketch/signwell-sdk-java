package com.signwell.sdk.resources;

import com.signwell.sdk.internal.SdkFutures;
import com.signwell.sdk.models.MeResponse;
import com.signwell.sdk.raw.MeApi;
import java.util.concurrent.CompletableFuture;

public final class MeResource {
    private final MeApi raw;

    public MeResource(MeApi raw) {
        this.raw = raw;
    }

    public MeApi raw() {
        return raw;
    }

    public CompletableFuture<MeResponse> get() {
        return SdkFutures.callRequired(raw::getMe, "SignWell returned an empty account response.");
    }
}
