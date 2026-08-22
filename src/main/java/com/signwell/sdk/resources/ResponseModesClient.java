package com.signwell.sdk.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.signwell.sdk.SignWellOptions;
import com.signwell.sdk.errors.UnsupportedContentTypeException;
import com.signwell.sdk.internal.ExceptionMapper;
import com.signwell.sdk.models.*;
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.ApiException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public final class ResponseModesClient {
    private final SignWellOptions options;
    private final HttpClient http;
    private final ObjectMapper mapper;

    public ResponseModesClient(SignWellOptions options) {
        this(options, options.createHttpClient(), options.createObjectMapper());
    }

    public ResponseModesClient(SignWellOptions options, HttpClient http, ObjectMapper mapper) {
        this.options = Objects.requireNonNull(options, "options");
        this.http = Objects.requireNonNull(http, "http");
        this.mapper = Objects.requireNonNull(mapper, "mapper");
    }

    public CompletableFuture<OwnedResponseStream> completedPdfStream(
            UUID id, boolean auditPage, FileFormat format) {
        Objects.requireNonNull(id, "id");
        return stream(
                "/api/v1/documents/"
                        + id
                        + "/completed_pdf/?url_only=false&audit_page="
                        + auditPage
                        + fileFormat(format));
    }

    public CompletableFuture<CompletedPdfUrlResponse> completedPdfUrl(
            UUID id, boolean auditPage, FileFormat format) {
        Objects.requireNonNull(id, "id");
        return json(
                "/api/v1/documents/"
                        + id
                        + "/completed_pdf/?url_only=true&audit_page="
                        + auditPage
                        + fileFormat(format),
                CompletedPdfUrlResponse.class);
    }

    public CompletableFuture<OwnedResponseStream> bulkSendCsvStream(List<UUID> ids) {
        return stream(bulkPath(ids, false));
    }

    public CompletableFuture<BulkSendCsvTemplateResponse> bulkSendCsvBase64(List<UUID> ids) {
        return json(bulkPath(ids, true), BulkSendCsvTemplateResponse.class);
    }

    public CompletableFuture<OwnedResponseStream> nom151Stream(UUID id) {
        return stream(nomPath(Objects.requireNonNull(id, "id"), false, false));
    }

    public CompletableFuture<Nom151UrlResponse> nom151Url(UUID id) {
        return json(
                nomPath(Objects.requireNonNull(id, "id"), true, false), Nom151UrlResponse.class);
    }

    public CompletableFuture<Nom151CertificateResponse> nom151Object(UUID id) {
        return json(
                nomPath(Objects.requireNonNull(id, "id"), false, true),
                Nom151CertificateResponse.class);
    }

    private CompletableFuture<OwnedResponseStream> stream(String path) {
        HttpRequest request =
                request(path)
                        .header(
                                "Accept",
                                "application/pdf, application/zip, text/csv,"
                                        + " application/octet-stream")
                        .GET()
                        .build();
        return http.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream())
                .handle(
                        (response, failure) -> {
                            if (failure != null) throw ExceptionMapper.map(failure);
                            if (response.statusCode() / 100 != 2) throw status(response);
                            String contentType = contentType(response.headers());
                            if (!(contentType.equals("application/pdf")
                                    || contentType.equals("application/zip")
                                    || contentType.equals("application/octet-stream")
                                    || contentType.equals("text/csv"))) {
                                close(response.body());
                                throw new UnsupportedContentTypeException(
                                        contentType, "PDF, ZIP, CSV, or binary content");
                            }
                            return new OwnedResponseStream(
                                    response.statusCode(), response.headers(), response.body());
                        });
    }

    private <T> CompletableFuture<T> json(String path, Class<T> type) {
        HttpRequest request = request(path).header("Accept", "application/json").GET().build();
        return http.sendAsync(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
                .handle(
                        (response, failure) -> {
                            if (failure != null) throw ExceptionMapper.map(failure);
                            if (response.statusCode() / 100 != 2)
                                throw status(
                                        response.statusCode(), response.headers(), response.body());
                            String contentType = contentType(response.headers());
                            if (!(contentType.equals("application/json")
                                    || contentType.endsWith("+json")))
                                throw new UnsupportedContentTypeException(
                                        contentType, "JSON content");
                            try {
                                T result = mapper.readValue(response.body(), type);
                                if (result == null)
                                    throw new com.signwell.sdk.errors.SignWellException(
                                            "SignWell returned an empty JSON response.");
                                return result;
                            } catch (IOException failureToParse) {
                                throw new com.signwell.sdk.errors.SignWellException(
                                        "SignWell returned invalid JSON.", failureToParse);
                            }
                        });
    }

    private HttpRequest.Builder request(String path) {
        return HttpRequest.newBuilder(endpoint(path))
                .timeout(options.getTimeout())
                .setHeader("X-Api-Key", options.getApiKey())
                .setHeader("User-Agent", SignWellOptions.USER_AGENT);
    }

    private URI endpoint(String path) {
        return URI.create(options.getBaseUri().toString() + path);
    }

    private static String fileFormat(FileFormat format) {
        return format == null ? "" : "&file_format=" + ApiClient.urlEncode(format.toString());
    }

    private static String nomPath(UUID id, boolean urlOnly, boolean objectOnly) {
        return "/api/v1/documents/"
                + id
                + "/nom151_certificate/?url_only="
                + urlOnly
                + "&object_only="
                + objectOnly;
    }

    private static String bulkPath(List<UUID> ids, boolean base64) {
        if (ids == null || ids.isEmpty())
            throw new IllegalArgumentException("At least one template ID is required.");
        if (ids.stream().anyMatch(Objects::isNull))
            throw new IllegalArgumentException("Template IDs cannot contain null values.");
        String query =
                ids.stream()
                        .map(id -> "template_ids%5B%5D=" + ApiClient.urlEncode(id.toString()))
                        .collect(Collectors.joining("&"));
        return "/api/v1/bulk_sends/csv_template/?" + query + "&base64=" + base64;
    }

    private RuntimeException status(HttpResponse<InputStream> response) {
        String body = "";
        try (InputStream stream = response.body()) {
            body = new String(stream.readNBytes(65_536), StandardCharsets.UTF_8);
        } catch (IOException ignored) {
        }
        return status(response.statusCode(), response.headers(), body);
    }

    private RuntimeException status(int status, HttpHeaders headers, String body) {
        return ExceptionMapper.map(
                new ApiException(status, "SignWell API request failed.", headers, body));
    }

    private static String contentType(HttpHeaders headers) {
        return headers.firstValue("content-type")
                .orElse("")
                .split(";", 2)[0]
                .trim()
                .toLowerCase(Locale.ROOT);
    }

    private static void close(InputStream stream) {
        try {
            stream.close();
        } catch (IOException ignored) {
        }
    }
}
