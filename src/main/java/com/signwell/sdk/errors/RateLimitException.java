package com.signwell.sdk.errors;

import com.signwell.sdk.raw.ApiException;

public final class RateLimitException extends ApiStatusException {
    public RateLimitException(ApiException cause) {
        super(429, cause.getResponseBody(), cause.getResponseHeaders(), cause);
    }
}
