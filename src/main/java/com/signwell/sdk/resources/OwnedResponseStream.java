package com.signwell.sdk.resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpHeaders;

/** Non-buffered response stream. Closing this object closes the HTTP body. */
public final class OwnedResponseStream implements AutoCloseable {
    private final int statusCode;
    private final HttpHeaders headers;
    private final InputStream stream;

    public OwnedResponseStream(int statusCode, HttpHeaders headers, InputStream stream) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.stream = stream;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public InputStream getStream() {
        return stream;
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }
}
