package com.signwell.sdk.errors;

import com.signwell.sdk.models.DocumentResponse;

public final class WaitForCompletionTimeoutException extends SignWellException {
    private final DocumentResponse lastResponse;

    public WaitForCompletionTimeoutException(DocumentResponse lastResponse) {
        super("Timed out while waiting for the SignWell document to reach a terminal status.");
        this.lastResponse = lastResponse;
    }

    public DocumentResponse getLastResponse() {
        return lastResponse;
    }
}
