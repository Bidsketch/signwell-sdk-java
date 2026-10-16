package com.signwell.sdk.resources;

import static org.junit.jupiter.api.Assertions.*;

import com.signwell.sdk.SignWellOptions;
import com.signwell.sdk.errors.ApiConnectionException;
import com.signwell.sdk.errors.ApiTimeoutException;
import com.signwell.sdk.errors.RateLimitException;
import com.signwell.sdk.errors.SignWellException;
import com.signwell.sdk.errors.UnsupportedContentTypeException;
import com.signwell.sdk.internal.RateLimitHttpClient;
import com.signwell.sdk.models.CompletedPdfUrlResponse;
import com.signwell.sdk.models.MeResponse;
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import com.signwell.sdk.raw.ApiResponse;
import com.signwell.sdk.raw.DocumentApi;
import com.signwell.sdk.raw.MeApi;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import org.junit.jupiter.api.Test;

class ResponseModesClientTest {
    @Test
    void rawClientDecodesSuccessAndErrorBodiesAsUtf8() throws ApiException {
        QueueHttpClient http = new QueueHttpClient();
        DocumentApi raw = new DocumentApi(rawApiClient(http));
        String name = "Contrat caf\u00e9 \u2014 \u6771\u4eac";
        http.enqueue(
                201,
                Map.of("Content-Type", List.of("application/json")),
                "{\"name\":\"" + name + "\"}");
        assertEquals(
                name,
                raw.createDocument(new com.signwell.sdk.models.DocumentRequest()).join().getName());

        String errorBody = "{\"message\":\"" + name + "\"}";
        http.enqueue(400, Map.of("Content-Type", List.of("application/json")), errorBody);
        CompletionException failure =
                assertThrows(
                        CompletionException.class,
                        () ->
                                raw.createDocument(new com.signwell.sdk.models.DocumentRequest())
                                        .join());
        assertEquals(
                errorBody,
                assertInstanceOf(ApiException.class, failure.getCause()).getResponseBody());
    }

    @Test
    void rawClientRetriesRateLimitsAndReplaysTheSamePostBody() throws ApiException {
        QueueHttpClient http = new QueueHttpClient();
        TrackingStream rejected = new TrackingStream(new byte[] {1});
        http.enqueue(429, Map.of("Retry-After", List.of("0")), rejected);
        http.enqueue(
                201,
                Map.of("Content-Type", List.of("application/json")),
                "{\"id\":\"3c1e4f50-6c2d-4c41-a793-e8e507ab9e77\"}");
        DocumentApi raw = new DocumentApi(rawApiClient(http));
        assertEquals(
                UUID.fromString("3c1e4f50-6c2d-4c41-a793-e8e507ab9e77"),
                raw.createDocument(
                                new com.signwell.sdk.models.DocumentRequest()
                                        .name("Retry document")
                                        .testMode(true))
                        .join()
                        .getId());
        assertEquals(2, http.requests.size());
        assertEquals("POST", http.lastRequest.method());
        assertEquals(requestBody(http.requests.get(0)), requestBody(http.requests.get(1)));
        assertTrue(requestBody(http.lastRequest).contains("\"name\":\"Retry document\""));
        assertTrue(rejected.closed);
    }

    @Test
    void rawClientRetryLimitAndOptOutPreserveRateLimitErrors() {
        for (int retries : new int[] {0, 2}) {
            QueueHttpClient http = new QueueHttpClient();
            for (int i = 0; i <= retries; i++)
                http.enqueue(
                        429,
                        Map.of(
                                "Retry-After",
                                List.of("0"),
                                "Content-Type",
                                List.of("application/json")),
                        "{\"message\":\"limited\"}");
            MeResource resource =
                    new MeResource(new MeApi(rawApiClient(http).setMaxRateLimitRetries(retries)));
            assertInstanceOf(RateLimitException.class, failure(resource.get()));
            assertEquals(retries + 1, http.requests.size());
        }
    }

    @Test
    void downloadRetriesCloseRejectedStreamsAndCancellationStopsRetries() throws IOException {
        QueueHttpClient http = new QueueHttpClient();
        TrackingStream rejected = new TrackingStream(new byte[] {1});
        TrackingStream pdf = new TrackingStream(new byte[] {2});
        http.enqueue(429, Map.of("Retry-After", List.of("0")), rejected);
        http.enqueue(200, Map.of("Content-Type", List.of("application/pdf")), pdf);
        SignWellOptions options = SignWellOptions.builder("test-key").build();
        ResponseModesClient modes =
                new ResponseModesClient(
                        options, new RateLimitHttpClient(http, 2), options.createObjectMapper());
        try (OwnedResponseStream stream =
                modes.completedPdfStream(UUID.randomUUID(), true, null).join()) {
            assertEquals(200, stream.getStatusCode());
            assertEquals(2, http.requests.size());
            assertTrue(rejected.closed);
        }
        assertTrue(pdf.closed);

        QueueHttpClient waiting = new QueueHttpClient();
        TrackingStream limited = new TrackingStream(new byte[] {1});
        waiting.enqueue(429, Map.of("Retry-After", List.of("30")), limited);
        CompletableFuture<HttpResponse<InputStream>> pending =
                new RateLimitHttpClient(waiting, 2)
                        .sendAsync(
                                HttpRequest.newBuilder(URI.create("https://example.com"))
                                        .GET()
                                        .build(),
                                HttpResponse.BodyHandlers.ofInputStream());
        assertTrue(pending.cancel(true));
        assertTrue(pending.isCancelled());
        assertEquals(1, waiting.requests.size());
        assertTrue(limited.closed);
    }

    @Test
    void serverErrorsAndExcessiveRetryAfterAreNotRetried() {
        for (int status : new int[] {500, 429}) {
            QueueHttpClient http = new QueueHttpClient();
            http.enqueue(
                    status,
                    Map.of(
                            "Retry-After",
                            List.of("120"),
                            "Content-Type",
                            List.of("application/json")),
                    "{}");
            assertInstanceOf(
                    com.signwell.sdk.errors.ApiStatusException.class,
                    failure(new MeResource(new MeApi(rawApiClient(http))).get()));
            assertEquals(1, http.requests.size());
        }
    }

    @Test
    void cancellingThePublicResourceFutureStopsScheduledRetries() throws InterruptedException {
        QueueHttpClient http = new QueueHttpClient();
        http.enqueue(429, Map.of("Retry-After", List.of("1")), new TrackingStream(new byte[] {1}));
        http.enqueue(200, Map.of("Content-Type", List.of("application/json")), "{}");
        CompletableFuture<?> result = new MeResource(new MeApi(rawApiClient(http))).get();
        assertTrue(result.cancel(true));
        TimeUnit.MILLISECONDS.sleep(1100);
        assertEquals(1, http.requests.size());
    }

    private static String requestBody(HttpRequest request) {
        CompletableFuture<String> result = new CompletableFuture<>();
        StringBuilder body = new StringBuilder();
        request.bodyPublisher()
                .orElseThrow()
                .subscribe(
                        new java.util.concurrent.Flow.Subscriber<java.nio.ByteBuffer>() {
                            @Override
                            public void onSubscribe(
                                    java.util.concurrent.Flow.Subscription subscription) {
                                subscription.request(Long.MAX_VALUE);
                            }

                            @Override
                            public void onNext(java.nio.ByteBuffer buffer) {
                                body.append(StandardCharsets.UTF_8.decode(buffer));
                            }

                            @Override
                            public void onError(Throwable failure) {
                                result.completeExceptionally(failure);
                            }

                            @Override
                            public void onComplete() {
                                result.complete(body.toString());
                            }
                        });
        return result.join();
    }

    @Test
    void getMePreservesUuidMembershipIds() throws ApiException {
        // Membership ids are AccountRole external ids, always UUIDs (Trello 2708, AC-04), so
        // MeResponse.id stays a UUID; only the user ids are plain strings (see the legacy test).
        // Same API-shaped fixture as that test, read through both the resource and the raw client.
        QueueHttpClient http = new QueueHttpClient();
        MeApi raw = new MeApi(rawApiClient(http));
        MeResource me = new MeResource(raw);
        UUID membershipId = UUID.fromString("3c1e4f50-6c2d-4c41-a793-e8e507ab9e77");

        http.enqueue(200, Map.of("Content-Type", List.of("application/json")), LEGACY_ME_RESPONSE);
        assertEquals(membershipId, me.get().join().getId());
        assertEquals("/api/v1/me", http.lastRequest.uri().getPath());

        http.enqueue(200, Map.of("Content-Type", List.of("application/json")), LEGACY_ME_RESPONSE);
        assertEquals(membershipId, raw.getMeWithHttpInfo().join().getData().getId());
    }

    @Test
    void streamsWithoutBufferingOwnsBodyAndSendsSafeHeaders() throws Exception {
        QueueHttpClient http = new QueueHttpClient();
        TrackingStream body = new TrackingStream("large-pdf-body".getBytes(StandardCharsets.UTF_8));
        http.enqueue(200, Map.of("Content-Type", List.of("application/pdf")), body);
        ResponseModesClient modes = modes(http);

        UUID id = UUID.randomUUID();
        OwnedResponseStream response = modes.completedPdfStream(id, true, null).join();
        assertSame(body, response.getStream());
        assertEquals(0, body.readCount);
        assertEquals(200, response.getStatusCode());
        assertEquals("test-key", http.lastRequest.headers().firstValue("X-Api-Key").orElseThrow());
        assertEquals(
                SignWellOptions.USER_AGENT,
                http.lastRequest.headers().firstValue("User-Agent").orElseThrow());
        assertTrue(http.lastRequest.uri().toString().contains(id.toString()));
        assertTrue(http.lastRequest.uri().getQuery().contains("url_only=false"));
        response.close();
        assertTrue(body.closed);
    }

    @Test
    void everyJsonAndBinaryModeUsesExplicitWireFlags() {
        QueueHttpClient http = new QueueHttpClient();
        ResponseModesClient modes = modes(http);
        UUID id = UUID.randomUUID();

        http.enqueue(
                200,
                Map.of("Content-Type", List.of("application/json")),
                "{\"file_url\":\"https://www.signwell.com/download\"}");
        CompletedPdfUrlResponse url = modes.completedPdfUrl(id, false, null).join();
        assertEquals("https://www.signwell.com/download", url.getFileUrl());
        assertTrue(http.lastRequest.uri().getQuery().contains("url_only=true"));
        assertTrue(http.lastRequest.uri().getQuery().contains("audit_page=false"));

        http.enqueue(
                200,
                Map.of("Content-Type", List.of("text/csv")),
                new ByteArrayInputStream("csv".getBytes(StandardCharsets.UTF_8)));
        modes.bulkSendCsvStream(List.of(id)).join();
        assertTrue(http.lastRequest.uri().getQuery().contains("base64=false"));
        assertTrue(http.lastRequest.uri().getRawQuery().contains("template_ids%5B%5D"));

        http.enqueue(
                200, Map.of("Content-Type", List.of("application/json")), "{\"data\":\"Y3N2\"}");
        modes.bulkSendCsvBase64(List.of(id)).join();
        assertTrue(http.lastRequest.uri().getQuery().contains("base64=true"));

        http.enqueue(
                200,
                Map.of("Content-Type", List.of("application/zip")),
                new ByteArrayInputStream(new byte[] {1, 2, 3}));
        modes.nom151Stream(id).join();
        assertTrue(http.lastRequest.uri().getQuery().contains("url_only=false"));
        assertTrue(http.lastRequest.uri().getQuery().contains("object_only=false"));

        http.enqueue(200, Map.of("Content-Type", List.of("application/json")), "{}");
        modes.nom151Url(id).join();
        assertTrue(http.lastRequest.uri().getQuery().contains("url_only=true"));

        http.enqueue(200, Map.of("Content-Type", List.of("application/json")), "{}");
        modes.nom151Object(id).join();
        assertTrue(http.lastRequest.uri().getQuery().contains("object_only=true"));

        assertThrows(IllegalArgumentException.class, () -> modes.bulkSendCsvStream(List.of()));
        assertThrows(
                IllegalArgumentException.class,
                () -> modes.bulkSendCsvStream(java.util.Arrays.asList(id, null)));
    }

    @Test
    void rejectsEmptyJsonResponses() {
        QueueHttpClient http = new QueueHttpClient();
        http.enqueue(200, Map.of("Content-Type", List.of("application/json")), "null");

        Throwable failure = failure(modes(http).completedPdfUrl(UUID.randomUUID(), false, null));

        assertInstanceOf(SignWellException.class, failure);
        assertEquals("SignWell returned an empty JSON response.", failure.getMessage());
    }

    @Test
    void contentTypeStatusRateLimitTimeoutAndConnectionFailuresAreTypedAndRedacted() {
        QueueHttpClient http = new QueueHttpClient();
        ResponseModesClient modes = modes(http);
        UUID id = UUID.randomUUID();

        TrackingStream wrong = new TrackingStream(new byte[] {1});
        http.enqueue(200, Map.of("Content-Type", List.of("text/html")), wrong);
        Throwable unsupported = failure(modes.completedPdfStream(id, true, null));
        assertInstanceOf(UnsupportedContentTypeException.class, unsupported);
        assertEquals(
                "text/html",
                ((UnsupportedContentTypeException) unsupported).getActualContentType());
        assertTrue(wrong.closed);

        String secretBody = "{\"error\":\"private-response-body\"}";
        http.enqueue(
                429,
                Map.of(
                        "Content-Type",
                        List.of("application/json"),
                        "RateLimit-Limit",
                        List.of("100"),
                        "RateLimit-Remaining",
                        List.of("0"),
                        "Retry-After",
                        List.of("7")),
                new ByteArrayInputStream(secretBody.getBytes(StandardCharsets.UTF_8)));
        Throwable limited = failure(modes.completedPdfStream(id, true, null));
        assertInstanceOf(RateLimitException.class, limited);
        RateLimitException rate = (RateLimitException) limited;
        assertEquals(secretBody, rate.getResponseBody());
        assertEquals(100L, rate.getRateLimitInfo().getLimit());
        assertEquals(Duration.ofSeconds(7), rate.getRateLimitInfo().getRetryAfter());
        assertNotNull(rate.getResponse());
        assertFalse(rate.getMessage().contains("private-response-body"));
        assertFalse(rate.getMessage().contains("test-key"));
        assertFalse(rate.getMessage().contains("url_only"));

        http.fail(new HttpTimeoutException("timeout with https://host?secret=true"));
        Throwable timeout = failure(modes.completedPdfUrl(id, true, null));
        assertInstanceOf(ApiTimeoutException.class, timeout);
        assertInstanceOf(HttpTimeoutException.class, timeout.getCause());
        assertFalse(timeout.getMessage().contains("secret"));

        http.fail(new IOException("connection with api key"));
        Throwable connection = failure(modes.completedPdfUrl(id, true, null));
        assertInstanceOf(ApiConnectionException.class, connection);
        assertInstanceOf(IOException.class, connection.getCause());
    }

    @Test
    void rawClientsPreserveResponseMetadataAndRedactDefaultErrorMessages() throws Exception {
        QueueHttpClient http = new QueueHttpClient();
        DocumentApi raw = new DocumentApi(rawApiClient(http));
        UUID id = UUID.randomUUID();

        String body = "{\"error\":\"private-response-body\"}";
        http.enqueue(404, Map.of("Content-Type", List.of("application/json")), body);
        Throwable failure = failure(raw.getDocument(id));
        assertInstanceOf(ApiException.class, failure);
        ApiException apiFailure = (ApiException) failure;
        assertEquals(body, apiFailure.getResponseBody());
        assertFalse(apiFailure.getMessage().contains("private-response-body"));

        http.enqueue(
                200,
                Map.of(
                        "Content-Type", List.of("application/json"),
                        "X-Request-Id", List.of("request-123")),
                "{\"id\":\"" + id + "\"}");
        ApiResponse<com.signwell.sdk.models.DocumentResponse> response =
                raw.getDocumentWithHttpInfo(id).join();
        assertEquals(200, response.getStatusCode());
        assertEquals("request-123", response.getHeaders().get("X-Request-Id").get(0));
        assertEquals(id, response.getData().getId());
    }

    @Test
    void getMeDeserializesLegacyNumericUserIds() {
        // Users present at docsketch's Feb 2020 external_id backfill kept their AccountRole numeric
        // id, so
        // the API returns user ids such as "3" for them. The spec used to declare user.id and
        // active_users[].id as format: uuid, which made this UUID-typed field throw on every legacy
        // account (Bidsketch/docsketch#4774). Membership, account and contact ids are real UUIDs.
        QueueHttpClient http = new QueueHttpClient();
        MeResource me = new MeResource(new MeApi(rawApiClient(http)));
        http.enqueue(200, Map.of("Content-Type", List.of("application/json")), LEGACY_ME_RESPONSE);

        MeResponse response = me.get().join();

        assertEquals("3", response.getUser().getId());
        assertEquals("3", response.getAccount().getActiveUsers().get(0).getId());
        assertEquals("3", response.getWorkspace().getActiveUsers().get(0).getId());
        // Membership, account and contact ids keep format: uuid; these pin that only the user ids
        // lost it, so dropping the format from the wrong line in the same schema block fails here.
        assertEquals(UUID.fromString("3c1e4f50-6c2d-4c41-a793-e8e507ab9e77"), response.getId());
        assertEquals(
                UUID.fromString("00d90639-dac7-4eb4-aa13-db2c3e87c874"),
                response.getAccount().getId());
        assertEquals(
                UUID.fromString("264e865a-c78b-44a2-a4cb-5991b50db840"),
                response.getContact().getId());
    }

    private static final String LEGACY_ME_RESPONSE =
            "{\"id\":\"3c1e4f50-6c2d-4c41-a793-e8e507ab9e77\",\"role\":\"owner\",\"archived\":false,\"user\":{\"id\":\"3\",\"name\":\"Legacy"
                + " Owner\",\"email\":\"owner@legacy.example\",\"has_google_registration\":false,\"first_name\":\"Legacy\"},\"account\":{\"id\":\"00d90639-dac7-4eb4-aa13-db2c3e87c874\",\"name\":\"Legacy"
                + " LLC\",\"plan_tier\":\"free\",\"active_templates\":0,\"can_create_template\":true,\"can_create_tracking_document\":true,\"can_create_completion_document\":true,\"active_users\":[{\"id\":\"3\",\"name\":\"Legacy"
                + " Owner\",\"email\":\"owner@legacy.example\",\"has_google_registration\":false}]},\"workspace\":{\"id\":\"00d90639-dac7-4eb4-aa13-db2c3e87c874\",\"name\":\"Legacy"
                + " LLC\",\"plan_tier\":\"free\",\"active_templates\":0,\"can_create_template\":true,\"can_create_tracking_document\":true,\"can_create_completion_document\":true,\"active_users\":[{\"id\":\"3\",\"name\":\"Legacy"
                + " Owner\",\"email\":\"owner@legacy.example\",\"has_google_registration\":false}]},\"contact\":{\"id\":\"264e865a-c78b-44a2-a4cb-5991b50db840\",\"email\":\"owner@legacy.example\",\"name\":\"Legacy"
                + " Owner\",\"company_name\":\"Legacy LLC\",\"phone_number\":\"970 470"
                + " 3370\",\"alt_phone_number\":\"(873)"
                + " 602-9900\",\"website\":\"https://legacy.example\",\"initials\":\"LO\",\"archived\":false}}";

    private static ApiClient rawApiClient(QueueHttpClient http) {
        SignWellOptions options =
                SignWellOptions.builder("test-key")
                        .baseUri(URI.create("https://api.example.com"))
                        .build();
        return new ApiClient(
                new QueueHttpClientBuilder(http),
                options.createObjectMapper(),
                options.getBaseUri().toString());
    }

    private static ResponseModesClient modes(QueueHttpClient http) {
        SignWellOptions options =
                SignWellOptions.builder("test-key")
                        .baseUri(URI.create("https://api.example.com/prefix"))
                        .build();
        return new ResponseModesClient(options, http, options.createObjectMapper());
    }

    private static Throwable failure(CompletableFuture<?> future) {
        CompletionException failure = assertThrows(CompletionException.class, future::join);
        return failure.getCause();
    }

    private static final class TrackingStream extends ByteArrayInputStream {
        private int readCount;
        private boolean closed;

        private TrackingStream(byte[] bytes) {
            super(bytes);
        }

        @Override
        public synchronized int read() {
            readCount++;
            return super.read();
        }

        @Override
        public synchronized int read(byte[] bytes, int offset, int length) {
            readCount++;
            return super.read(bytes, offset, length);
        }

        @Override
        public void close() throws IOException {
            closed = true;
            super.close();
        }
    }

    private static final class QueueHttpClient extends HttpClient {
        private final Queue<Object> outcomes = new ArrayDeque<>();
        private final java.util.List<HttpRequest> requests = new java.util.ArrayList<>();
        private HttpRequest lastRequest;

        private void enqueue(int status, Map<String, List<String>> headers, Object body) {
            outcomes.add(new FakeResponse<>(status, headers, body));
        }

        private void fail(Throwable failure) {
            outcomes.add(failure);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> CompletableFuture<HttpResponse<T>> sendAsync(
                HttpRequest request, HttpResponse.BodyHandler<T> handler) {
            lastRequest = request;
            requests.add(request);
            Object outcome = outcomes.remove();
            if (outcome instanceof Throwable)
                return CompletableFuture.failedFuture((Throwable) outcome);
            FakeResponse<?> response = (FakeResponse<?>) outcome;
            if (response.body() instanceof String) {
                HttpResponse.BodySubscriber<T> subscriber =
                        handler.apply(
                                new HttpResponse.ResponseInfo() {
                                    @Override
                                    public int statusCode() {
                                        return response.statusCode();
                                    }

                                    @Override
                                    public HttpHeaders headers() {
                                        return response.headers();
                                    }

                                    @Override
                                    public HttpClient.Version version() {
                                        return response.version();
                                    }
                                });
                subscriber.onSubscribe(
                        new java.util.concurrent.Flow.Subscription() {
                            @Override
                            public void request(long count) {}

                            @Override
                            public void cancel() {}
                        });
                subscriber.onNext(
                        List.of(
                                java.nio.ByteBuffer.wrap(
                                        ((String) response.body())
                                                .getBytes(StandardCharsets.UTF_8))));
                subscriber.onComplete();
                return subscriber
                        .getBody()
                        .toCompletableFuture()
                        .thenApply(
                                body ->
                                        new FakeResponse<T>(
                                                response.statusCode(),
                                                response.headers().map(),
                                                body));
            }
            return CompletableFuture.completedFuture((HttpResponse<T>) outcome);
        }

        @Override
        public <T> CompletableFuture<HttpResponse<T>> sendAsync(
                HttpRequest request,
                HttpResponse.BodyHandler<T> handler,
                HttpResponse.PushPromiseHandler<T> pushPromiseHandler) {
            return sendAsync(request, handler);
        }

        @Override
        public <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> handler) {
            return sendAsync(request, handler).join();
        }

        @Override
        public Optional<CookieHandler> cookieHandler() {
            return Optional.empty();
        }

        @Override
        public Optional<Duration> connectTimeout() {
            return Optional.of(Duration.ofSeconds(30));
        }

        @Override
        public Redirect followRedirects() {
            return Redirect.NEVER;
        }

        @Override
        public Optional<ProxySelector> proxy() {
            return Optional.empty();
        }

        @Override
        public SSLContext sslContext() {
            try {
                return SSLContext.getDefault();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }

        @Override
        public SSLParameters sslParameters() {
            return new SSLParameters();
        }

        @Override
        public Optional<Authenticator> authenticator() {
            return Optional.empty();
        }

        @Override
        public Version version() {
            return Version.HTTP_2;
        }

        @Override
        public Optional<Executor> executor() {
            return Optional.empty();
        }
    }

    private static final class QueueHttpClientBuilder implements HttpClient.Builder {
        private final HttpClient client;

        private QueueHttpClientBuilder(HttpClient client) {
            this.client = client;
        }

        @Override
        public HttpClient.Builder cookieHandler(CookieHandler cookieHandler) {
            return this;
        }

        @Override
        public HttpClient.Builder connectTimeout(Duration duration) {
            return this;
        }

        @Override
        public HttpClient.Builder sslContext(SSLContext sslContext) {
            return this;
        }

        @Override
        public HttpClient.Builder sslParameters(SSLParameters sslParameters) {
            return this;
        }

        @Override
        public HttpClient.Builder executor(Executor executor) {
            return this;
        }

        @Override
        public HttpClient.Builder followRedirects(HttpClient.Redirect policy) {
            return this;
        }

        @Override
        public HttpClient.Builder version(HttpClient.Version version) {
            return this;
        }

        @Override
        public HttpClient.Builder priority(int priority) {
            return this;
        }

        @Override
        public HttpClient.Builder proxy(ProxySelector proxySelector) {
            return this;
        }

        @Override
        public HttpClient.Builder authenticator(Authenticator authenticator) {
            return this;
        }

        @Override
        public HttpClient build() {
            return client;
        }
    }

    private static final class FakeResponse<T> implements HttpResponse<T> {
        private final int status;
        private final HttpHeaders headers;
        private final T body;

        @SuppressWarnings("unchecked")
        private FakeResponse(int status, Map<String, List<String>> headers, Object body) {
            this.status = status;
            this.headers = HttpHeaders.of(headers, (name, value) -> true);
            this.body = (T) body;
        }

        @Override
        public int statusCode() {
            return status;
        }

        @Override
        public HttpRequest request() {
            return null;
        }

        @Override
        public Optional<HttpResponse<T>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            return headers;
        }

        @Override
        public T body() {
            return body;
        }

        @Override
        public Optional<SSLSession> sslSession() {
            return Optional.empty();
        }

        @Override
        public URI uri() {
            return URI.create("https://api.example.com");
        }

        @Override
        public HttpClient.Version version() {
            return HttpClient.Version.HTTP_2;
        }
    }
}
