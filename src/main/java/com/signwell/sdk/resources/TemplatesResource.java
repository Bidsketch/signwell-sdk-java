package com.signwell.sdk.resources;

import com.signwell.sdk.internal.RequestValidator;
import com.signwell.sdk.internal.SdkFutures;
import com.signwell.sdk.models.*;
import com.signwell.sdk.raw.TemplateApi;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class TemplatesResource {
    private final TemplateApi raw;

    public TemplatesResource(TemplateApi raw) {
        this.raw = raw;
    }

    public TemplateApi raw() {
        return raw;
    }

    public CompletableFuture<DocumentTemplateResponse> create(DocumentTemplateRequest request) {
        RequestValidator.validate(request);
        return SdkFutures.callRequired(
                () -> raw.createTemplate(request), "SignWell returned an empty template response.");
    }

    public CompletableFuture<DocumentTemplateResponse> get(UUID id) {
        return SdkFutures.callRequired(
                () -> raw.getTemplate(Objects.requireNonNull(id, "id")),
                "SignWell returned an empty template response.");
    }

    public CompletableFuture<DocumentTemplateResponse> update(
            UUID id, DocumentTemplateUpdateRequest request) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(request, "request");
        return SdkFutures.callRequired(
                () -> raw.updateTemplate(id, request),
                "SignWell returned an empty template response.");
    }

    public CompletableFuture<Void> delete(UUID id) {
        return SdkFutures.call(() -> raw.deleteTemplate(Objects.requireNonNull(id, "id")));
    }

    public CompletableFuture<DocumentTemplateListResponse> list(String query, int page, int limit) {
        DocumentsResource.validateList(query, page, limit);
        return SdkFutures.callRequired(
                () -> raw.listTemplates(page, Math.min(limit, 50), query),
                "SignWell returned an empty template list.");
    }

    public CompletableFuture<DocumentTemplateListResponse> list() {
        return list(null, 1, 50);
    }

    public AsyncPageIterator<DocumentTemplateListResponse, DocumentTemplateResponse> pages(
            String query, int page, int limit) {
        DocumentsResource.validateList(query, page, limit);
        int clamped = Math.min(limit, 50);
        return new AsyncPageIterator<>(
                page,
                next -> list(query, next, clamped),
                DocumentTemplateListResponse::getNextPage,
                DocumentTemplateListResponse::getTemplates);
    }

    public AsyncPageIterator<DocumentTemplateListResponse, DocumentTemplateResponse> pages() {
        return pages(null, 1, 50);
    }

    public AsyncPageIterator<DocumentTemplateListResponse, DocumentTemplateResponse> items() {
        return pages();
    }
}
