package com.signwell.sdk.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;

/** Retries explicit rate limit responses while preserving the configured HTTP client. */
public final class RateLimitHttpClient extends HttpClient {
    private final HttpClient delegate;
    private final int maxRetries;

    public RateLimitHttpClient(HttpClient delegate, int maxRetries) {
        if (maxRetries < 0 || maxRetries > 10)
            throw new IllegalArgumentException("Rate limit retries must be between zero and ten.");
        this.delegate = delegate;
        this.maxRetries = maxRetries;
    }

    @Override
    public <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> handler)
            throws IOException, InterruptedException {
        for (int attempt = 0; ; attempt++) {
            HttpResponse<T> response = delegate.send(request, handler);
            Duration delay = retryDelay(response.headers(), attempt, Instant.now());
            if (response.statusCode() != 429 || attempt >= maxRetries || delay == null)
                return response;
            close(response.body());
            TimeUnit.MILLISECONDS.sleep(delay.toMillis());
        }
    }

    @Override
    public <T> CompletableFuture<HttpResponse<T>> sendAsync(
            HttpRequest request, HttpResponse.BodyHandler<T> handler) {
        return sendAsync(request, handler, null);
    }

    @Override
    public <T> CompletableFuture<HttpResponse<T>> sendAsync(
            HttpRequest request,
            HttpResponse.BodyHandler<T> handler,
            HttpResponse.PushPromiseHandler<T> pushHandler) {
        CompletableFuture<HttpResponse<T>> result = new RetryFuture<>();
        attempt(request, handler, pushHandler, 0, result);
        return result;
    }

    private <T> void attempt(
            HttpRequest request,
            HttpResponse.BodyHandler<T> handler,
            HttpResponse.PushPromiseHandler<T> pushHandler,
            int attempt,
            CompletableFuture<HttpResponse<T>> result) {
        if (result.isDone()) return;
        CompletableFuture<HttpResponse<T>> pending =
                pushHandler == null
                        ? delegate.sendAsync(request, handler)
                        : delegate.sendAsync(request, handler, pushHandler);
        result.whenComplete(
                (ignored, failure) -> {
                    if (result.isCancelled()) pending.cancel(true);
                });
        pending.whenComplete(
                (response, failure) -> {
                    if (result.isDone()) {
                        if (response != null) close(response.body());
                        return;
                    }
                    if (failure != null) {
                        result.completeExceptionally(failure);
                        return;
                    }
                    Duration delay = retryDelay(response.headers(), attempt, Instant.now());
                    if (response.statusCode() != 429 || attempt >= maxRetries || delay == null) {
                        if (!result.complete(response)) close(response.body());
                        return;
                    }
                    close(response.body());
                    CompletableFuture.runAsync(
                                    () ->
                                            attempt(
                                                    request,
                                                    handler,
                                                    pushHandler,
                                                    attempt + 1,
                                                    result),
                                    CompletableFuture.delayedExecutor(
                                            delay.toMillis(), TimeUnit.MILLISECONDS))
                            .whenComplete(
                                    (ignored, retryFailure) -> {
                                        if (retryFailure != null)
                                            result.completeExceptionally(retryFailure);
                                    });
                });
    }

    static Duration retryDelay(HttpHeaders headers, int attempt, Instant now) {
        Duration delay = Duration.ofSeconds(1L << Math.min(attempt, 5));
        String value = headers.firstValue("Retry-After").orElse(null);
        if (value != null) {
            try {
                long seconds = Long.parseLong(value.trim());
                if (seconds >= 0) delay = Duration.ofSeconds(seconds);
            } catch (NumberFormatException invalidSeconds) {
                try {
                    delay =
                            Duration.between(
                                    now,
                                    ZonedDateTime.parse(
                                                    value.trim(),
                                                    DateTimeFormatter.RFC_1123_DATE_TIME)
                                            .toInstant());
                } catch (DateTimeParseException invalidDate) {
                    // Invalid Retry-After values use exponential backoff.
                }
            }
        }
        if (delay.compareTo(Duration.ofMinutes(1)) > 0) return null;
        return delay.isNegative() ? Duration.ZERO : delay;
    }

    private static void close(Object body) {
        if (body instanceof InputStream) {
            try {
                ((InputStream) body).close();
            } catch (IOException ignored) {
            }
        }
    }

    private static final class RetryFuture<T> extends CompletableFuture<T> {
        private final CompletableFuture<?> source;

        private RetryFuture() {
            source = this;
        }

        private RetryFuture(CompletableFuture<?> source) {
            this.source = source;
        }

        @Override
        public <U> CompletableFuture<U> newIncompleteFuture() {
            return new RetryFuture<>(source);
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            boolean cancelled = super.cancel(mayInterruptIfRunning);
            if (cancelled && source != this) source.cancel(mayInterruptIfRunning);
            return cancelled;
        }
    }

    @Override
    public Optional<CookieHandler> cookieHandler() {
        return delegate.cookieHandler();
    }

    @Override
    public Optional<Duration> connectTimeout() {
        return delegate.connectTimeout();
    }

    @Override
    public Redirect followRedirects() {
        return delegate.followRedirects();
    }

    @Override
    public Optional<ProxySelector> proxy() {
        return delegate.proxy();
    }

    @Override
    public SSLContext sslContext() {
        return delegate.sslContext();
    }

    @Override
    public SSLParameters sslParameters() {
        return delegate.sslParameters();
    }

    @Override
    public Optional<Authenticator> authenticator() {
        return delegate.authenticator();
    }

    @Override
    public Version version() {
        return delegate.version();
    }

    @Override
    public Optional<Executor> executor() {
        return delegate.executor();
    }
}
