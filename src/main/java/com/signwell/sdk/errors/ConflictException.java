package com.signwell.sdk.errors;

import com.signwell.sdk.raw.ApiException;

public final class ConflictException extends ApiStatusException {
    public ConflictException(ApiException cause) {
        super(409, cause.getResponseBody(), cause.getResponseHeaders(), cause);
    }
}
