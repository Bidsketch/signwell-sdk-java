package com.signwell.sdk.errors;

public final class WebhookReplayCapacityException extends WebhookVerificationException {
    public WebhookReplayCapacityException() {
        super("Webhook replay store capacity is exhausted.");
    }
}
