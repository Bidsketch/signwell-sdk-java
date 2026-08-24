package com.signwell.sdk.errors;

import com.signwell.sdk.raw.ApiException;

public final class AuthenticationException extends ApiStatusException {
    public AuthenticationException(ApiException cause) {
        super(401, cause.getResponseBody(), cause.getResponseHeaders(), cause);
    }
}
