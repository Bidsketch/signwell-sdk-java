package com.signwell.sdk.errors;

import com.signwell.sdk.raw.ApiException;

public final class InternalServerException extends ApiStatusException {
    public InternalServerException(ApiException cause) {
        super(cause.getCode(), cause.getResponseBody(), cause.getResponseHeaders(), cause);
    }
}
