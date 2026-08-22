package com.signwell.sdk.resources;

import com.signwell.sdk.internal.SdkFutures;
import com.signwell.sdk.models.*;
import com.signwell.sdk.raw.BulkSendApi;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class BulkSendsResource {
    private final BulkSendApi raw;
    private final ResponseModesClient responseModes;

    public BulkSendsResource(BulkSendApi raw, ResponseModesClient responseModes) {
        this.raw = raw;
        this.responseModes = responseModes;
    }

    public BulkSendApi raw() {
        return raw;
    }

    public CompletableFuture<BulkSendCreateResponse> create(CreateBulkSendRequest request) {
        return SdkFutures.callRequired(
                () -> raw.createBulkSend(Objects.requireNonNull(request, "request")),
                "SignWell returned an empty bulk-send response.");
    }

    public CompletableFuture<BulkSendValidateCsvResponse> validateCsv(BulkSendCsvRequest request) {
        return SdkFutures.callRequired(
                () -> raw.validateBulkSendCsv(Objects.requireNonNull(request, "request")),
                "SignWell returned an empty bulk-send validation response.");
    }

    public CompletableFuture<BulkSendResponse> get(UUID id) {
        return SdkFutures.callRequired(
                () -> raw.getBulkSend(Objects.requireNonNull(id, "id")),
                "SignWell returned an empty bulk-send response.");
    }

    public CompletableFuture<BulkSendDocumentsResponse> documents(UUID id, int limit, int page) {
        validatePage(limit, page);
        return SdkFutures.callRequired(
                () ->
                        raw.getBulkSendDocuments(
                                Objects.requireNonNull(id, "id"), Math.min(limit, 50), page),
                "SignWell returned an empty bulk-send document list.");
    }

    public CompletableFuture<BulkSendListResponse> list(
            String userEmail, int limit, int page, UUID apiApplicationId) {
        validatePage(limit, page);
        return SdkFutures.callRequired(
                () -> raw.listBulkSends(userEmail, Math.min(limit, 50), page, apiApplicationId),
                "SignWell returned an empty bulk-send list.");
    }

    public CompletableFuture<BulkSendListResponse> list() {
        return list(null, 50, 1, null);
    }

    public CompletableFuture<OwnedResponseStream> csvStream(List<UUID> templateIds) {
        return responseModes.bulkSendCsvStream(templateIds);
    }

    public CompletableFuture<BulkSendCsvTemplateResponse> csvBase64(List<UUID> templateIds) {
        return responseModes.bulkSendCsvBase64(templateIds);
    }

    private static void validatePage(int limit, int page) {
        if (limit < 1) throw new IllegalArgumentException("Limit must be at least 1.");
        if (page < 1) throw new IllegalArgumentException("Page must be at least 1.");
    }
}
