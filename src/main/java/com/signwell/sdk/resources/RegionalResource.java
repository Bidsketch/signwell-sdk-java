package com.signwell.sdk.resources;

import com.signwell.sdk.models.Nom151CertificateResponse;
import com.signwell.sdk.models.Nom151UrlResponse;
import com.signwell.sdk.raw.RegionalApi;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class RegionalResource {
    private final RegionalApi raw;
    private final ResponseModesClient responseModes;

    public RegionalResource(RegionalApi raw, ResponseModesClient responseModes) {
        this.raw = raw;
        this.responseModes = responseModes;
    }

    public RegionalApi raw() {
        return raw;
    }

    public CompletableFuture<OwnedResponseStream> nom151Stream(UUID id) {
        return responseModes.nom151Stream(id);
    }

    public CompletableFuture<Nom151UrlResponse> nom151Url(UUID id) {
        return responseModes.nom151Url(id);
    }

    public CompletableFuture<Nom151CertificateResponse> nom151Object(UUID id) {
        return responseModes.nom151Object(id);
    }
}
