package com.signwell.sdk.errors;

import com.fasterxml.jackson.databind.JsonNode;
import com.signwell.sdk.raw.ApiClient;
import java.net.http.HttpHeaders;

/** HTTP status failure with response metadata. Messages never contain response bodies. */
public class ApiStatusException extends SignWellException {
    private final int statusCode;
    private final String responseBody;
    private final JsonNode response;
    private final HttpHeaders responseHeaders;
    private final RateLimitInfo rateLimitInfo;

    public ApiStatusException(
            int statusCode, String responseBody, HttpHeaders responseHeaders, Throwable cause) {
        super("SignWell API request failed with HTTP status " + statusCode + ".", cause);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.response = parse(responseBody);
        this.responseHeaders = responseHeaders;
        this.rateLimitInfo = RateLimitInfo.from(responseHeaders);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public JsonNode getResponse() {
        return response;
    }

    public HttpHeaders getResponseHeaders() {
        return responseHeaders;
    }

    public RateLimitInfo getRateLimitInfo() {
        return rateLimitInfo;
    }

    public RateLimitInfo getRateLimit() {
        return rateLimitInfo;
    }

    private static JsonNode parse(String body) {
        if (body == null || body.isBlank()) return null;
        try {
            return ApiClient.createDefaultObjectMapper().readTree(body);
        } catch (Exception ignored) {
            return null;
        }
    }
}
