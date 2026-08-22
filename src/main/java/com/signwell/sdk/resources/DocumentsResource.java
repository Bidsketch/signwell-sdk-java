package com.signwell.sdk.resources;

import com.signwell.sdk.errors.WaitForCompletionTimeoutException;
import com.signwell.sdk.internal.RequestValidator;
import com.signwell.sdk.internal.SdkFutures;
import com.signwell.sdk.models.*;
import com.signwell.sdk.raw.DocumentApi;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public final class DocumentsResource {
    private static final Set<String> TERMINAL =
            Collections.unmodifiableSet(
                    new HashSet<>(
                            Arrays.asList(
                                    "completed",
                                    "manually completed",
                                    "declined",
                                    "canceled",
                                    "bounced",
                                    "blocked",
                                    "error",
                                    "expired")));
    private final DocumentApi raw;
    private final ResponseModesClient responseModes;

    public DocumentsResource(DocumentApi raw, ResponseModesClient responseModes) {
        this.raw = raw;
        this.responseModes = responseModes;
    }

    public DocumentApi raw() {
        return raw;
    }

    public CompletableFuture<DocumentResponse> create(DocumentRequest request) {
        RequestValidator.validate(request);
        return SdkFutures.callRequired(
                () -> raw.createDocument(request), "SignWell returned an empty document response.");
    }

    public CompletableFuture<DocumentFromTemplateResponse> createFromTemplate(
            DocumentFromTemplateRequest request) {
        RequestValidator.validate(request);
        return SdkFutures.callRequired(
                () -> raw.createDocumentFromTemplate(request),
                "SignWell returned an empty document response.");
    }

    public CompletableFuture<DocumentResponse> get(UUID id) {
        return SdkFutures.callRequired(
                () -> raw.getDocument(Objects.requireNonNull(id, "id")),
                "SignWell returned an empty document response.");
    }

    public CompletableFuture<DocumentResponse> send(UUID id, UpdateDocumentAndSendRequest request) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(request, "request");
        return SdkFutures.callRequired(
                () -> raw.sendDocument(id, request),
                "SignWell returned an empty document response.");
    }

    public CompletableFuture<DocumentResponse> updateDocument(
            UUID id, UpdateDocumentAndSendRequest request) {
        return send(id, request);
    }

    public CompletableFuture<Void> sendReminder(UUID id, SendReminderRequest request) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(request, "request");
        return SdkFutures.call(() -> raw.sendReminder(id, request));
    }

    public CompletableFuture<DocumentResponse> updateRecipients(
            UUID id, UpdateRecipientsRequest request) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(request, "request");
        return SdkFutures.callRequired(
                () -> raw.updateRecipients(id, request),
                "SignWell returned an empty document response.");
    }

    public CompletableFuture<DocumentResponse> updateAuthentication(
            UUID id, UpdateAuthenticationRequest request) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(request, "request");
        return SdkFutures.callRequired(
                () -> raw.updateAuthentication(id, request),
                "SignWell returned an empty document response.");
    }

    public CompletableFuture<Void> delete(UUID id) {
        return SdkFutures.call(() -> raw.deleteDocument(Objects.requireNonNull(id, "id")));
    }

    public CompletableFuture<DocumentListResponse> list(String query, int page, int limit) {
        validateList(query, page, limit);
        return SdkFutures.callRequired(
                () -> raw.listDocuments(page, Math.min(limit, 50), query),
                "SignWell returned an empty document list.");
    }

    public CompletableFuture<DocumentListResponse> list() {
        return list(null, 1, 50);
    }

    public AsyncPageIterator<DocumentListResponse, DocumentResponse> pages(
            String query, int page, int limit) {
        validateList(query, page, limit);
        int clamped = Math.min(limit, 50);
        return new AsyncPageIterator<>(
                page,
                next -> list(query, next, clamped),
                DocumentListResponse::getNextPage,
                DocumentListResponse::getDocuments);
    }

    public AsyncPageIterator<DocumentListResponse, DocumentResponse> pages() {
        return pages(null, 1, 50);
    }

    public AsyncPageIterator<DocumentListResponse, DocumentResponse> items() {
        return pages();
    }

    public CompletableFuture<DocumentResponse> waitForCompletion(UUID id) {
        return waitForCompletion(id, Duration.ofSeconds(2), Duration.ofMinutes(2), null, TERMINAL);
    }

    public CompletableFuture<DocumentResponse> waitForCompletion(
            UUID id,
            Duration interval,
            Duration timeout,
            Integer maximumAttempts,
            Collection<String> statuses) {
        if (interval == null || interval.isZero() || interval.isNegative())
            throw new IllegalArgumentException("Polling interval must be positive.");
        if (timeout == null || timeout.isZero() || timeout.isNegative())
            throw new IllegalArgumentException("Polling timeout must be positive.");
        if (maximumAttempts != null && maximumAttempts < 1)
            throw new IllegalArgumentException("Maximum attempts must be positive.");
        if (statuses == null || statuses.isEmpty())
            throw new IllegalArgumentException("At least one terminal status is required.");
        Set<String> normalized = new HashSet<>();
        statuses.forEach(value -> normalized.add(value.toLowerCase(Locale.ROOT)));
        CompletableFuture<DocumentResponse> result = new CompletableFuture<>();
        long timeoutNanos;
        try {
            timeoutNanos = timeout.toNanos();
        } catch (ArithmeticException ignored) {
            timeoutNanos = Long.MAX_VALUE;
        }
        poll(
                id,
                interval,
                System.nanoTime(),
                timeoutNanos,
                maximumAttempts,
                normalized,
                1,
                null,
                result);
        return result;
    }

    private void poll(
            UUID id,
            Duration interval,
            long started,
            long timeoutNanos,
            Integer maximumAttempts,
            Set<String> statuses,
            int attempt,
            DocumentResponse last,
            CompletableFuture<DocumentResponse> result) {
        if (result.isDone()) return;
        if (System.nanoTime() - started >= timeoutNanos
                || (maximumAttempts != null && attempt > maximumAttempts)) {
            result.completeExceptionally(new WaitForCompletionTimeoutException(last));
            return;
        }
        get(id).whenComplete(
                        (document, failure) -> {
                            if (failure != null) {
                                result.completeExceptionally(failure);
                                return;
                            }
                            String status = document.getStatus();
                            if (status != null
                                    && statuses.contains(status.toLowerCase(Locale.ROOT))) {
                                result.complete(document);
                                return;
                            }
                            if (maximumAttempts != null && attempt >= maximumAttempts) {
                                result.completeExceptionally(
                                        new WaitForCompletionTimeoutException(document));
                                return;
                            }
                            CompletableFuture.delayedExecutor(
                                            interval.toMillis(),
                                            java.util.concurrent.TimeUnit.MILLISECONDS)
                                    .execute(
                                            () ->
                                                    poll(
                                                            id,
                                                            interval,
                                                            started,
                                                            timeoutNanos,
                                                            maximumAttempts,
                                                            statuses,
                                                            attempt + 1,
                                                            document,
                                                            result));
                        });
    }

    public CompletableFuture<OwnedResponseStream> getCompletedPdfStream(
            UUID id, boolean auditPage, FileFormat format) {
        return responseModes.completedPdfStream(id, auditPage, format);
    }

    public CompletableFuture<CompletedPdfUrlResponse> getCompletedPdfUrl(
            UUID id, boolean auditPage, FileFormat format) {
        return responseModes.completedPdfUrl(id, auditPage, format);
    }

    public CompletableFuture<OwnedResponseStream> getCompletedPdfStream(UUID id) {
        return getCompletedPdfStream(id, true, null);
    }

    public CompletableFuture<CompletedPdfUrlResponse> getCompletedPdfUrl(UUID id) {
        return getCompletedPdfUrl(id, true, null);
    }

    static void validateList(String query, int page, int limit) {
        if (query != null && query.trim().isEmpty())
            throw new IllegalArgumentException("Query cannot be blank.");
        if (page < 1) throw new IllegalArgumentException("Page must be at least 1.");
        if (limit < 1) throw new IllegalArgumentException("Limit must be at least 1.");
    }
}
