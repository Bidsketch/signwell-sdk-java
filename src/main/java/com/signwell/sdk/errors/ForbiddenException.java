package com.signwell.sdk.errors;

import com.signwell.sdk.raw.ApiException;

public class ForbiddenException extends ApiStatusException {
    public ForbiddenException(ApiException cause) {
        super(403, cause.getResponseBody(), cause.getResponseHeaders(), cause);
    }
}
