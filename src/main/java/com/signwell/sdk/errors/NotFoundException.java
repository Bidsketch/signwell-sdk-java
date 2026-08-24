package com.signwell.sdk.errors;

import com.signwell.sdk.raw.ApiException;

public final class NotFoundException extends ApiStatusException {
    public NotFoundException(ApiException cause) {
        super(404, cause.getResponseBody(), cause.getResponseHeaders(), cause);
    }
}
