package com.signwell.sdk;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.signwell.sdk.internal.CheckboxValueModule;
import com.signwell.sdk.raw.ApiClient;
import java.net.URI;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Objects;
import java.util.function.Consumer;

/** Immutable client configuration. */
public final class SignWellOptions {
    public static final String DEFAULT_BASE_URL = "https://www.signwell.com";
    public static final String USER_AGENT = "SignWell Java SDK/" + sdkVersion();

    private final String apiKey;
    private final URI baseUri;
    private final Duration timeout;
    private final Consumer<HttpClient.Builder> httpClientCustomizer;
    private final Consumer<ObjectMapper> objectMapperCustomizer;

    private SignWellOptions(Builder builder) {
        apiKey = requireApiKey(builder.apiKey);
        baseUri = normalizeBaseUri(builder.baseUri);
        timeout = Objects.requireNonNull(builder.timeout, "timeout");
        if (timeout.isZero() || timeout.isNegative())
            throw new IllegalArgumentException("Timeout must be positive.");
        httpClientCustomizer = builder.httpClientCustomizer;
        objectMapperCustomizer = builder.objectMapperCustomizer;
    }

    public static Builder builder(String apiKey) {
        return new Builder(apiKey);
    }

    public ApiClient createRawClient() {
        ObjectMapper mapper = ApiClient.createDefaultObjectMapper();
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
        mapper.registerModule(new CheckboxValueModule());
        if (objectMapperCustomizer != null) objectMapperCustomizer.accept(mapper);
        return new ApiClient(httpClientBuilder(), mapper, baseUri.toString())
                .setReadTimeout(timeout)
                .setRequestInterceptor(
                        request -> {
                            request.setHeader("X-Api-Key", apiKey);
                            request.setHeader("User-Agent", USER_AGENT);
                        });
    }

    public HttpClient createHttpClient() {
        return httpClientBuilder().build();
    }

    public ObjectMapper createObjectMapper() {
        ObjectMapper mapper = ApiClient.createDefaultObjectMapper();
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
        mapper.registerModule(new CheckboxValueModule());
        if (objectMapperCustomizer != null) objectMapperCustomizer.accept(mapper);
        return mapper;
    }

    public String getApiKey() {
        return apiKey;
    }

    public URI getBaseUri() {
        return baseUri;
    }

    public Duration getTimeout() {
        return timeout;
    }

    private HttpClient.Builder httpClientBuilder() {
        HttpClient.Builder builder =
                HttpClient.newBuilder()
                        .connectTimeout(timeout)
                        .followRedirects(HttpClient.Redirect.NEVER);
        if (httpClientCustomizer != null) httpClientCustomizer.accept(builder);
        return builder;
    }

    private static String sdkVersion() {
        String version = SignWellOptions.class.getPackage().getImplementationVersion();
        return version == null ? "0.1.0-beta.1" : version;
    }

    private static String requireApiKey(String value) {
        if (value == null || value.trim().isEmpty())
            throw new IllegalArgumentException("A nonblank API key is required.");
        return value.trim();
    }

    private static URI normalizeBaseUri(URI value) {
        URI uri = value == null ? URI.create(DEFAULT_BASE_URL) : value;
        if (!uri.isAbsolute()
                || !"https".equalsIgnoreCase(uri.getScheme())
                || uri.getHost() == null)
            throw new IllegalArgumentException("Base URL must be an absolute HTTPS URL.");
        if (uri.getUserInfo() != null || uri.getQuery() != null || uri.getFragment() != null)
            throw new IllegalArgumentException(
                    "Base URL must not contain credentials, a query, or a fragment.");
        String text = uri.toString();
        while (text.endsWith("/")) text = text.substring(0, text.length() - 1);
        return URI.create(text);
    }

    public static final class Builder {
        private final String apiKey;
        private URI baseUri = URI.create(DEFAULT_BASE_URL);
        private Duration timeout = Duration.ofSeconds(30);
        private Consumer<HttpClient.Builder> httpClientCustomizer;
        private Consumer<ObjectMapper> objectMapperCustomizer;

        private Builder(String apiKey) {
            this.apiKey = apiKey;
        }

        public Builder baseUri(URI value) {
            baseUri = value;
            return this;
        }

        public Builder timeout(Duration value) {
            timeout = value;
            return this;
        }

        public Builder customizeHttpClient(Consumer<HttpClient.Builder> value) {
            httpClientCustomizer = value;
            return this;
        }

        public Builder customizeObjectMapper(Consumer<ObjectMapper> value) {
            objectMapperCustomizer = value;
            return this;
        }

        public SignWellOptions build() {
            return new SignWellOptions(this);
        }
    }
}
