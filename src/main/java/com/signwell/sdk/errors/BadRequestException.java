package com.signwell.sdk.errors;

import com.signwell.sdk.raw.ApiException;

public final class BadRequestException extends ApiStatusException {
    public BadRequestException(ApiException cause) {
        super(400, cause.getResponseBody(), cause.getResponseHeaders(), cause);
    }
}
