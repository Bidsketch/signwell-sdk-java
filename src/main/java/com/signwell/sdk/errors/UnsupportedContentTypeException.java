package com.signwell.sdk.errors;

public final class UnsupportedContentTypeException extends SignWellException {
    private final String actualContentType;
    private final String expectedContentType;

    public UnsupportedContentTypeException(String actual, String expected) {
        super("SignWell returned an unsupported content type; expected " + expected + ".");
        actualContentType = actual;
        expectedContentType = expected;
    }

    public String getActualContentType() {
        return actualContentType;
    }

    public String getExpectedContentType() {
        return expectedContentType;
    }
}
