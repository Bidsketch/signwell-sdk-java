package com.signwell.sdk;

import com.signwell.sdk.embedded.Embedded;
import com.signwell.sdk.models.*;
import com.signwell.sdk.raw.*;
import com.signwell.sdk.resources.*;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/** Primary entry point for raw APIs, resource helpers, embedded workflows, and response modes. */
public final class SignWellClient {
    private final DocumentsResource documents;
    private final TemplatesResource templates;
    private final BulkSendsResource bulkSends;
    private final RegionalResource regional;
    private final MeResource me;
    private final ApiApplicationsResource apiApplications;
    private final WebhooksResource webhooks;
    private final Embedded embedded;

    public SignWellClient(String apiKey) {
        this(SignWellOptions.builder(apiKey).build());
    }

    public SignWellClient(String apiKey, URI baseUri) {
        this(SignWellOptions.builder(apiKey).baseUri(baseUri).build());
    }

    public SignWellClient(SignWellOptions options) {
        ApiClient rawClient = options.createRawClient();
        ResponseModesClient responseModes =
                new ResponseModesClient(
                        options, rawClient.getHttpClient(), options.createObjectMapper());
        documents = new DocumentsResource(new DocumentApi(rawClient), responseModes);
        templates = new TemplatesResource(new TemplateApi(rawClient));
        bulkSends = new BulkSendsResource(new BulkSendApi(rawClient), responseModes);
        regional = new RegionalResource(new RegionalApi(rawClient), responseModes);
        me = new MeResource(new MeApi(rawClient));
        apiApplications = new ApiApplicationsResource(new ApiApplicationApi(rawClient));
        webhooks = new WebhooksResource(new WebhooksApi(rawClient));
        embedded = new Embedded(documents);
    }

    public DocumentsResource documents() {
        return documents;
    }

    public TemplatesResource templates() {
        return templates;
    }

    public BulkSendsResource bulkSends() {
        return bulkSends;
    }

    public RegionalResource regional() {
        return regional;
    }

    public MeResource me() {
        return me;
    }

    public ApiApplicationsResource apiApplications() {
        return apiApplications;
    }

    public WebhooksResource webhooks() {
        return webhooks;
    }

    public Embedded embedded() {
        return embedded;
    }

    public CompletableFuture<DocumentResponse> createDocument(DocumentRequest request) {
        return documents.create(request);
    }

    public CompletableFuture<DocumentFromTemplateResponse> createDocumentFromTemplate(
            DocumentFromTemplateRequest request) {
        return documents.createFromTemplate(request);
    }

    public CompletableFuture<DocumentResponse> sendDocument(
            UUID id, UpdateDocumentAndSendRequest request) {
        return documents.send(id, request);
    }

    public CompletableFuture<DocumentResponse> updateDocument(
            UUID id, UpdateDocumentAndSendRequest request) {
        return documents.updateDocument(id, request);
    }

    public CompletableFuture<Void> sendReminder(UUID id, SendReminderRequest request) {
        return documents.sendReminder(id, request);
    }

    public CompletableFuture<Void> sendReminder(UUID id) {
        return documents.sendReminder(id, new SendReminderRequest());
    }

    public CompletableFuture<MeResponse> getMe() {
        return me.get();
    }

    public CompletableFuture<DocumentTemplateResponse> createTemplate(
            DocumentTemplateRequest request) {
        return templates.create(request);
    }

    public CompletableFuture<DocumentTemplateResponse> updateTemplate(
            UUID id, DocumentTemplateUpdateRequest request) {
        return templates.update(id, request);
    }

    public CompletableFuture<BulkSendCreateResponse> createBulkSend(CreateBulkSendRequest request) {
        return bulkSends.create(request);
    }

    public CompletableFuture<BulkSendValidateCsvResponse> validateBulkSendCsv(
            BulkSendCsvRequest request) {
        return bulkSends.validateCsv(request);
    }

    public CompletableFuture<OwnedResponseStream> getCompletedPdfStream(UUID id) {
        return documents.getCompletedPdfStream(id, true, null);
    }

    public CompletableFuture<CompletedPdfUrlResponse> getCompletedPdfUrl(UUID id) {
        return documents.getCompletedPdfUrl(id, true, null);
    }

    public CompletableFuture<OwnedResponseStream> getBulkSendCsvStream(List<UUID> templateIds) {
        return bulkSends.csvStream(templateIds);
    }

    public CompletableFuture<BulkSendCsvTemplateResponse> getBulkSendCsvBase64(
            List<UUID> templateIds) {
        return bulkSends.csvBase64(templateIds);
    }

    public CompletableFuture<DocumentResponse> createSigningDocument(
            String name,
            List<Embedded.FileInput> files,
            List<Embedded.Recipient> recipients,
            List<List<Embedded.Field>> fields,
            boolean withSignaturePage,
            boolean textTags,
            boolean testMode,
            boolean sendNotifications) {
        return embedded.createSigningDocument(
                name,
                files,
                recipients,
                fields,
                withSignaturePage,
                textTags,
                testMode,
                sendNotifications);
    }

    public CompletableFuture<DocumentResponse> createRequestingDocument(
            String name,
            List<Embedded.FileInput> files,
            List<Embedded.Recipient> recipients,
            boolean testMode) {
        return embedded.createRequestingDocument(name, files, recipients, testMode);
    }

    public CompletableFuture<DocumentFromTemplateResponse> createSigningDocumentFromTemplate(
            Embedded.TemplateSelector selector,
            List<Embedded.TemplateRecipient> recipients,
            boolean testMode,
            boolean sendNotifications) {
        return embedded.createSigningDocumentFromTemplate(
                selector, recipients, testMode, sendNotifications);
    }

    public String embeddedSigningUrl(DocumentResponse document, int recipientIndex) {
        return Embedded.signingUrl(document, recipientIndex);
    }

    public Map<String, String> embeddedSigningUrls(DocumentResponse document) {
        return Embedded.signingUrls(document);
    }

    public String embeddedSigningUrl(DocumentFromTemplateResponse document, int recipientIndex) {
        return Embedded.signingUrl(document, recipientIndex);
    }

    public Map<String, String> embeddedSigningUrls(DocumentFromTemplateResponse document) {
        return Embedded.signingUrls(document);
    }

    public String scriptTag(String nonce) {
        return Embedded.scriptTag(nonce);
    }

    public String signingIframe(Embedded.IframeOptions options) {
        return Embedded.signingIframe(options);
    }

    public String requestingIframe(Embedded.IframeOptions options) {
        return Embedded.requestingIframe(options);
    }
}
