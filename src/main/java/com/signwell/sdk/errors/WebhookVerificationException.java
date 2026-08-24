package com.signwell.sdk.errors;

public class WebhookVerificationException extends SignWellException {
    public WebhookVerificationException(String message) {
        super(message);
    }

    public WebhookVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
