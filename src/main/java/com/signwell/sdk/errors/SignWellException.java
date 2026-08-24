package com.signwell.sdk.errors;

/** Base exception for the SignWell SDK. */
public class SignWellException extends RuntimeException {
    public SignWellException(String message) {
        super(message);
    }

    public SignWellException(String message, Throwable cause) {
        super(message, cause);
    }
}
