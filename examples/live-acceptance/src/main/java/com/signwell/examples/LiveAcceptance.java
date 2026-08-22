package com.signwell.examples;

import com.signwell.sdk.SignWellClient;
import com.signwell.sdk.embedded.Embedded;
import com.signwell.sdk.errors.ApiStatusException;
import com.signwell.sdk.errors.NotFoundException;
import com.signwell.sdk.models.*;
import com.signwell.sdk.resources.OwnedResponseStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletionException;

/** Destructive test-mode acceptance harness. Every created resource is deleted before exit. */
public final class LiveAcceptance {
    private static final String DEFAULT_PDF =
            "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";

    private LiveAcceptance() {}

    public static void main(String[] args) throws Exception {
        SignWellClient client = new SignWellClient(required("SIGNWELL_API_KEY"));
        List<UUID> documents = new ArrayList<>();
        List<UUID> templates = new ArrayList<>();
        try {
            run(client, documents, templates);
        } finally {
            Collections.reverse(documents);
            for (UUID id : documents) cleanup(() -> client.documents().delete(id).join(), "document", id);
            Collections.reverse(templates);
            for (UUID id : templates) cleanup(() -> client.templates().delete(id).join(), "template", id);
        }
    }

    private static void run(SignWellClient client, List<UUID> documents, List<UUID> templates)
            throws Exception {
        require(client.getMe().join() != null, "Account lookup returned null.");
        String email = optional("SIGNWELL_RECIPIENT_EMAIL", "signwell-sdk-live-" + System.currentTimeMillis() + "@example.com");
        String pdfUrl = optional("SIGNWELL_FIXTURE_FILE_URL", DEFAULT_PDF);
        byte[] pdf = download(pdfUrl);
        require(pdf.length > 5 && new String(pdf, 0, 5, StandardCharsets.US_ASCII).equals("%PDF-"), "Fixture is not a PDF.");
        System.out.println("PASS account lookup and HTTPS fixture");

        require(client.documents().pages().nextPage().join() != null, "Document pagination returned no page.");
        require(client.templates().pages().nextPage().join() != null, "Template pagination returned no page.");
        require(client.bulkSends().list().join() != null, "Bulk-send listing returned null.");
        require(client.webhooks().list().join() != null, "Webhook listing returned null.");
        System.out.println("PASS document, template, bulk-send, and webhook listing");

        DocumentResponse draft =
                client.createDocument(
                                new DocumentRequest()
                                        .testMode(true)
                                        .draft(true)
                                        .name("Java SDK live acceptance draft")
                                        .files(List.of(new FilesInner().name("fixture.pdf").fileUrl(pdfUrl)))
                                        .recipients(List.of(recipient(email)))
                                        .withSignaturePage(true)
                                        .embeddedSigning(true)
                                        .embeddedSigningNotifications(false))
                        .join();
        documents.add(draft.getId());
        require(client.documents().get(draft.getId()).join().getId().equals(draft.getId()), "Draft lookup failed.");
        System.out.println("PASS direct draft creation and lookup");

        String base64 = Base64.getEncoder().encodeToString(pdf);
        DocumentResponse requesting =
                client.createRequestingDocument(
                                "Java SDK live acceptance requesting",
                                List.of(Embedded.FileInput.base64("fixture.pdf", base64)),
                                List.of(new Embedded.Recipient("SDK Acceptance", email)),
                                true)
                        .join();
        documents.add(requesting.getId());
        System.out.println("PASS requesting draft creation");

        DocumentResponse signaturePage =
                client.createSigningDocument(
                                "Java SDK live acceptance signature page",
                                List.of(Embedded.FileInput.url("fixture.pdf", pdfUrl)),
                                List.of(new Embedded.Recipient("SDK Acceptance", email)),
                                null,
                                true,
                                false,
                                true,
                                false)
                        .join();
        documents.add(signaturePage.getId());
        require(Embedded.signingUrl(signaturePage, 0) != null, "Signature-page signing URL is missing.");

        DocumentResponse fields =
                client.createSigningDocument(
                                "Java SDK live acceptance fields",
                                List.of(Embedded.FileInput.url("fixture.pdf", pdfUrl)),
                                List.of(new Embedded.Recipient("SDK Acceptance", email)),
                                List.of(List.of(new Embedded.Field(72, 520, 1, FieldType.SIGNATURE, null, true, null))),
                                false,
                                false,
                                true,
                                false)
                        .join();
        documents.add(fields.getId());

        DocumentResponse textTags =
                client.createSigningDocument(
                                "Java SDK live acceptance text tags",
                                List.of(Embedded.FileInput.url("fixture.pdf", pdfUrl)),
                                List.of(new Embedded.Recipient("SDK Acceptance", email)),
                                null,
                                false,
                                true,
                                true,
                                false)
                        .join();
        documents.add(textTags.getId());
        System.out.println("PASS URL, base64, signature-page, field, and text-tag workflows");
        cooldown();

        UUID firstTemplate = createTemplate(client, templates, pdfUrl, null, "one");
        UUID secondTemplate = createTemplate(client, templates, null, pdf, "two");
        client.updateTemplate(firstTemplate, new DocumentTemplateUpdateRequest().name("Java SDK live acceptance template updated")).join();
        require(client.templates().get(firstTemplate).join().getId().equals(firstTemplate), "Template lookup failed.");

        Embedded.TemplateRecipient templateRecipient =
                new Embedded.TemplateRecipient("SDK Acceptance", email, "Signer", null, null);
        DocumentFromTemplateResponse single =
                client.createSigningDocumentFromTemplate(
                                Embedded.TemplateSelector.one(firstTemplate),
                                List.of(templateRecipient),
                                true,
                                false)
                        .join();
        documents.add(single.getId());
        DocumentFromTemplateResponse multiple =
                client.createSigningDocumentFromTemplate(
                                Embedded.TemplateSelector.many(List.of(firstTemplate, secondTemplate)),
                                List.of(templateRecipient),
                                true,
                                false)
                        .join();
        documents.add(multiple.getId());
        require(Embedded.signingUrl(single, 0) != null, "Single-template signing URL is missing.");
        require(Embedded.signingUrl(multiple, 0) != null, "Multi-template signing URL is missing.");
        System.out.println("PASS template create, update, get, single-template, and multi-template documents");

        List<UUID> templateIds = List.of(firstTemplate, secondTemplate);
        try (OwnedResponseStream csv = client.getBulkSendCsvStream(templateIds).join()) {
            require(csv.getStream().read() >= 0, "Bulk-send CSV stream is empty.");
        }
        byte[] csv = client.getBulkSendCsvBase64(templateIds).join().getData();
        require(csv != null && csv.length > 0, "Bulk-send base64 response is empty.");
        require(
                client.validateBulkSendCsv(
                                        new BulkSendCsvRequest()
                                                .templateIds(templateIds)
                                                .bulkSendCsv(Base64.getEncoder().encodeToString(csv)))
                                .join()
                        != null,
                "Bulk-send CSV validation returned null.");
        System.out.println("PASS bulk-send CSV stream, base64, and validation");
        cooldown();

        UUID completed = completedDocument(client);
        if (completed != null) {
            try (OwnedResponseStream pdfStream = client.getCompletedPdfStream(completed).join()) {
                require(pdfStream.getStream().read() >= 0, "Completed PDF stream is empty.");
            }
            require(client.getCompletedPdfUrl(completed).join().getFileUrl() != null, "Completed PDF URL is missing.");
            System.out.println("PASS completed PDF stream and URL");
        } else {
            System.out.println("SKIP completed PDF: no completed test-mode fixture was supplied or found");
        }

        UUID nom151 = uuid("SIGNWELL_NOM151_DOCUMENT_ID");
        if (nom151 != null) {
            try (OwnedResponseStream certificate = client.regional().nom151Stream(nom151).join()) {
                require(certificate.getStream().read() >= 0, "NOM-151 stream is empty.");
            }
            require(client.regional().nom151Url(nom151).join() != null, "NOM-151 URL response is missing.");
            require(client.regional().nom151Object(nom151).join() != null, "NOM-151 object response is missing.");
            System.out.println("PASS NOM-151 stream, URL, and object modes");
        } else {
            System.out.println("SKIP NOM-151: SIGNWELL_NOM151_DOCUMENT_ID was not supplied");
        }

        try {
            client.documents().get(UUID.randomUUID()).join();
            throw new IllegalStateException("Expected a typed not-found failure.");
        } catch (CompletionException failure) {
            require(failure.getCause() instanceof NotFoundException, "Not-found failure was not typed.");
        }
        System.out.println("PASS typed failure mapping");
    }

    private static UUID createTemplate(
            SignWellClient client,
            List<UUID> created,
            String pdfUrl,
            byte[] pdf,
            String suffix) {
        FilesInner file = new FilesInner().name("fixture.pdf");
        if (pdfUrl != null) file.fileUrl(pdfUrl);
        else file.fileBase64(pdf);
        DocumentTemplateResponse response =
                client.createTemplate(
                                new DocumentTemplateRequest()
                                        .name("Java SDK live acceptance template " + suffix)
                                        .draft(false)
                                        .files(List.of(file))
                                        .placeholders(List.of(new PlaceholdersInner().id("1").name("Signer")))
                                        .fields(
                                                List.of(
                                                        List.of(
                                                                new TemplateFieldsInnerInner()
                                                                        .x(72f)
                                                                        .y(520f)
                                                                        .page(1)
                                                                        .placeholderId("1")
                                                                        .type(FieldType.SIGNATURE)))))
                        .join();
        created.add(response.getId());
        return response.getId();
    }

    private static RecipientsInner recipient(String email) {
        return new RecipientsInner().id("1").name("SDK Acceptance").email(email);
    }

    private static UUID completedDocument(SignWellClient client) {
        UUID configured = uuid("SIGNWELL_COMPLETED_DOCUMENT_ID");
        if (configured != null) return configured;
        try {
            return client.documents().list("status:Completed", 1, 50).join().getDocuments().stream()
                    .filter(document -> Boolean.TRUE.equals(document.getTestMode()))
                    .map(DocumentResponse::getId)
                    .findFirst()
                    .orElse(null);
        } catch (ApiStatusException | CompletionException ignored) {
            return null;
        }
    }

    private static byte[] download(String url) throws Exception {
        HttpResponse<byte[]> response =
                HttpClient.newHttpClient()
                        .send(
                                HttpRequest.newBuilder(URI.create(url)).GET().build(),
                                HttpResponse.BodyHandlers.ofByteArray());
        require(response.statusCode() / 100 == 2, "Fixture download failed.");
        return response.body();
    }

    private static String required(String name) {
        String value = System.getenv(name);
        if (value == null || value.trim().isEmpty()) throw new IllegalStateException(name + " is required.");
        return value.trim();
    }

    private static String optional(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.trim().isEmpty() ? fallback : value.trim();
    }

    private static UUID uuid(String name) {
        String value = System.getenv(name);
        return value == null || value.trim().isEmpty() ? null : UUID.fromString(value.trim());
    }

    private static void cleanup(Runnable action, String type, UUID id) {
        try {
            action.run();
        } catch (RuntimeException failure) {
            System.err.println("Cleanup failed for " + type + " " + id + ": " + failure.getClass().getSimpleName());
        }
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new IllegalStateException(message);
    }

    private static void cooldown() throws InterruptedException {
        System.out.println("WAIT 60 seconds for the live API rate-limit window");
        Thread.sleep(60_000);
    }
}
