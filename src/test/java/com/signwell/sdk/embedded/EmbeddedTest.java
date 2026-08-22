package com.signwell.sdk.embedded;

import static org.junit.jupiter.api.Assertions.*;

import com.signwell.sdk.embedded.Embedded.Field;
import com.signwell.sdk.embedded.Embedded.FileInput;
import com.signwell.sdk.embedded.Embedded.IframeOptions;
import com.signwell.sdk.embedded.Embedded.Recipient;
import com.signwell.sdk.embedded.Embedded.TemplateRecipient;
import com.signwell.sdk.embedded.Embedded.TemplateSelector;
import com.signwell.sdk.models.*;
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.DocumentApi;
import com.signwell.sdk.resources.DocumentsResource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;

class EmbeddedTest {
    @Test
    void signingRequestingAndTemplateWorkflowsSerializeExpectedModels() {
        StubDocumentApi raw = new StubDocumentApi();
        Embedded embedded = new Embedded(new DocumentsResource(raw, null));
        FileInput file = FileInput.url("agreement.pdf", "https://files.example.com/agreement.pdf");
        List<Recipient> recipients =
                List.of(
                        new Recipient("Jane", "jane@example.com", null, 1234),
                        new Recipient("John", "john@example.com", "signer-two", true));
        List<List<Field>> fields =
                List.of(
                        List.of(
                                new Field(10, 20, 1, FieldType.SIGNATURE, null, true, null),
                                new Field(
                                        30,
                                        40,
                                        1,
                                        FieldType.SIGNATURE,
                                        "signer-two",
                                        true,
                                        "Sign")));

        raw.documentResponse = new DocumentResponse();
        embedded.createSigningDocument(
                        "Agreement", List.of(file), recipients, fields, false, false, true, false)
                .join();
        DocumentRequest signing = raw.documentRequest;
        assertAll(
                () -> assertEquals("1", signing.getRecipients().get(0).getId()),
                () -> assertEquals("1234", signing.getRecipients().get(0).getPasscode()),
                () -> assertEquals("true", signing.getRecipients().get(1).getPasscode()),
                () -> assertTrue(signing.getEmbeddedSigning()),
                () -> assertFalse(signing.getEmbeddedSigningNotifications()),
                () -> assertTrue(signing.getTestMode()),
                () -> assertEquals("1", signing.getFields().get(0).get(0).getRecipientId()));

        embedded.createRequestingDocument("Draft", List.of(file), recipients, true).join();
        assertTrue(raw.documentRequest.getDraft());
        assertFalse(raw.documentRequest.getEmbeddedSigning());

        raw.templateResponse = new DocumentFromTemplateResponse();
        UUID template = UUID.randomUUID();
        embedded.createSigningDocumentFromTemplate(
                        TemplateSelector.one(template),
                        List.of(
                                new TemplateRecipient(
                                        "Jane", "jane@example.com", "Signer 1", null, " 4321 ")),
                        true,
                        true)
                .join();
        assertEquals(template, raw.templateRequest.getTemplateId());
        assertNull(raw.templateRequest.getTemplateIds());
        assertEquals("4321", raw.templateRequest.getRecipients().get(0).getPasscode());

        List<UUID> templates = List.of(UUID.randomUUID(), UUID.randomUUID());
        embedded.createSigningDocumentFromTemplate(
                        TemplateSelector.many(templates),
                        List.of(
                                new TemplateRecipient(
                                        "Jane", "jane@example.com", null, null, null)),
                        false,
                        false)
                .join();
        assertNull(raw.templateRequest.getTemplateId());
        assertEquals(templates, raw.templateRequest.getTemplateIds());
    }

    @Test
    void embeddedInputsRejectUnsafeFilesSelectorsCheckboxesAndMissingSignerFields() {
        Embedded embedded = new Embedded(new DocumentsResource(new StubDocumentApi(), null));
        Recipient signer = new Recipient("Jane", "jane@example.com");

        assertThrows(
                IllegalArgumentException.class,
                () ->
                        embedded.createSigningDocument(
                                "Agreement",
                                List.of(
                                        new FileInput(
                                                "agreement.pdf",
                                                "https://example.com/a.pdf",
                                                "JVBERg==")),
                                List.of(signer),
                                null,
                                true,
                                false,
                                true,
                                false));
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        embedded.createSigningDocument(
                                "Agreement",
                                List.of(FileInput.base64("agreement.pdf", "not base64!")),
                                List.of(signer),
                                null,
                                true,
                                false,
                                true,
                                false));
        String incompletePdf =
                java.util.Base64.getEncoder()
                        .encodeToString(
                                "%PDF-1.7\nmissing trailer".getBytes(StandardCharsets.US_ASCII));
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        embedded.createSigningDocument(
                                "Agreement",
                                List.of(FileInput.base64("agreement.pdf", incompletePdf)),
                                List.of(signer),
                                null,
                                true,
                                false,
                                true,
                                false));
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        embedded.createSigningDocument(
                                "Agreement",
                                List.of(FileInput.url("agreement.pdf", "http://example.com/a.pdf")),
                                List.of(signer),
                                null,
                                true,
                                false,
                                true,
                                false));
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        embedded.createSigningDocument(
                                "Agreement",
                                List.of(
                                        FileInput.url(
                                                "agreement.pdf", "https://example.com/a.pdf")),
                                List.of(signer),
                                null,
                                false,
                                false,
                                true,
                                false));
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        embedded.createSigningDocumentFromTemplate(
                                TemplateSelector.many(List.of()),
                                List.of(
                                        new TemplateRecipient(
                                                "Jane", "jane@example.com", null, null, null)),
                                true,
                                false));

        for (Object accepted : List.of(true, false, "true", "false", "t", "f")) {
            assertDoesNotThrow(() -> Embedded.normalizeCheckbox(accepted));
        }
        for (Object rejected : List.of("yes", "no", "1", "0", 1, 0)) {
            assertThrows(
                    IllegalArgumentException.class, () -> Embedded.normalizeCheckbox(rejected));
        }
    }

    @Test
    void rendererUsesExactAllowlistsNonceEscapingAndSafeHandlerPaths() {
        String script = Embedded.scriptTag("nonce\"<&");
        assertTrue(script.contains("nonce=\"nonce&quot;&lt;&amp;\""));
        assertFalse(script.contains("nonce\"<&"));

        IframeOptions options =
                new IframeOptions("https://app.signwell.com/s/abc")
                        .containerId("</script><script>alert(1)</script>")
                        .nonce("safe-nonce")
                        .events(Map.of("completed", "SignWellHandlers.completed"))
                        .allowClose(true)
                        .showHeader(false);
        String rendered = Embedded.signingIframe(options);
        assertTrue(rendered.contains("nonce=\"safe-nonce\""));
        assertTrue(rendered.contains("SignWellHandlers.completed"));
        assertFalse(rendered.contains("</script><script>alert"));
        assertTrue(rendered.contains("\\u003c/script\\u003e"));

        assertThrows(
                IllegalArgumentException.class,
                () -> Embedded.signingIframe(new IframeOptions("http://app.signwell.com/s/abc")));
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        Embedded.signingIframe(
                                new IframeOptions("https://signwell.com.evil.test/s")));
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        Embedded.signingIframe(
                                new IframeOptions("https://embed.example.com/s")
                                        .allowedEmbedHosts(List.of("example.com"))));
        assertDoesNotThrow(
                () ->
                        Embedded.signingIframe(
                                new IframeOptions("https://embed.example.com/s")
                                        .allowedEmbedHosts(List.of("embed.example.com"))));
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        Embedded.signingIframe(
                                new IframeOptions("https://app.signwell.com/s")
                                        .redirectUrl("https://redirect.example.com/done")));
        assertDoesNotThrow(
                () ->
                        Embedded.signingIframe(
                                new IframeOptions("https://app.signwell.com/s")
                                        .redirectUrl("https://redirect.example.com/done")
                                        .allowedRedirectHosts(List.of("redirect.example.com"))));
        for (String handler :
                List.of(
                        "alert(1)",
                        "handler;evil()",
                        "handlers.constructor.call",
                        "handlers.__proto__.run",
                        "handlers[\"done\"]")) {
            assertThrows(
                    IllegalArgumentException.class,
                    () ->
                            Embedded.signingIframe(
                                    new IframeOptions("https://app.signwell.com/s")
                                            .events(Map.of("completed", handler))));
        }
    }

    @Test
    void signingUrlsExtractByIndexAndEmail() {
        DocumentResponse response =
                new DocumentResponse()
                        .recipients(
                                List.of(
                                        new DocumentResponseRecipientsInner()
                                                .email("Jane@example.com")
                                                .embeddedSigningUrl(
                                                        "https://app.signwell.com/s/one"),
                                        new DocumentResponseRecipientsInner()
                                                .email("john@example.com")
                                                .embeddedSigningUrl(
                                                        "https://app.signwell.com/s/two")));
        assertEquals("https://app.signwell.com/s/one", Embedded.signingUrl(response, 0));
        assertNull(Embedded.signingUrl(response, 3));
        assertEquals(
                "https://app.signwell.com/s/one",
                Embedded.signingUrls(response).get("jane@example.com"));
    }

    private static final class StubDocumentApi extends DocumentApi {
        private DocumentRequest documentRequest;
        private DocumentFromTemplateRequest templateRequest;
        private DocumentResponse documentResponse = new DocumentResponse();
        private DocumentFromTemplateResponse templateResponse = new DocumentFromTemplateResponse();

        StubDocumentApi() {
            super(new ApiClient());
        }

        @Override
        public CompletableFuture<DocumentResponse> createDocument(DocumentRequest request) {
            documentRequest = request;
            return CompletableFuture.completedFuture(documentResponse);
        }

        @Override
        public CompletableFuture<DocumentFromTemplateResponse> createDocumentFromTemplate(
                DocumentFromTemplateRequest request) {
            templateRequest = request;
            return CompletableFuture.completedFuture(templateResponse);
        }
    }
}
