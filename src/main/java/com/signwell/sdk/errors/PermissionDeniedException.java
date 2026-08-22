package com.signwell.sdk.errors;

import com.signwell.sdk.raw.ApiException;

public final class PermissionDeniedException extends ForbiddenException {
    public PermissionDeniedException(ApiException cause) {
        super(cause);
    }
}
