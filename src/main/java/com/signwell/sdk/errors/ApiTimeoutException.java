package com.signwell.sdk.errors;

public final class ApiTimeoutException extends TransportException {
    public ApiTimeoutException(Throwable cause) {
        super("The SignWell API request timed out.", cause);
    }
}
