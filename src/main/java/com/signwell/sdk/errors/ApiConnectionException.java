package com.signwell.sdk.errors;

public final class ApiConnectionException extends TransportException {
    public ApiConnectionException(Throwable cause) {
        super("Unable to connect to the SignWell API.", cause);
    }
}
