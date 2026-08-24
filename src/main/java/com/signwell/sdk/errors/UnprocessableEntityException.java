package com.signwell.sdk.errors;

import com.signwell.sdk.raw.ApiException;

public final class UnprocessableEntityException extends ApiStatusException {
    public UnprocessableEntityException(ApiException cause) {
        super(422, cause.getResponseBody(), cause.getResponseHeaders(), cause);
    }
}
