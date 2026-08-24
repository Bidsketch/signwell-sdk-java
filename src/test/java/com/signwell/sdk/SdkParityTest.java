package com.signwell.sdk;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.signwell.sdk.errors.WaitForCompletionTimeoutException;
import com.signwell.sdk.models.*;
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.raw.DocumentApi;
import com.signwell.sdk.raw.TemplateApi;
import com.signwell.sdk.resources.AsyncPageIterator;
import com.signwell.sdk.resources.DocumentsResource;
import com.signwell.sdk.resources.TemplatesResource;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import org.junit.jupiter.api.Test;

class SdkParityTest {
    @Test
    void clientRequiresSafeOptionsAndExposesEveryResource() {
        assertThrows(IllegalArgumentException.class, () -> new SignWellClient(" "));
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        SignWellOptions.builder("key")
                                .baseUri(URI.create("http://example.com"))
                                .build());
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        SignWellOptions.builder("key")
                                .baseUri(URI.create("https://user@example.com"))
                                .build());
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        SignWellOptions.builder("key")
                                .baseUri(URI.create("https://example.com?secret=value"))
                                .build());
        assertThrows(
                IllegalArgumentException.class,
                () -> SignWellOptions.builder("key").timeout(Duration.ZERO).build());

        SignWellOptions options =
                SignWellOptions.builder(" key ")
                        .baseUri(URI.create("https://example.com/prefix/"))
                        .build();
        assertEquals("key", options.getApiKey());
        assertEquals("https://example.com/prefix", options.getBaseUri().toString());
        assertEquals(Duration.ofSeconds(30), options.getTimeout());

        SignWellClient client = new SignWellClient(options);
        assertAll(
                () -> assertNotNull(client.documents()),
                () -> assertNotNull(client.templates()),
                () -> assertNotNull(client.bulkSends()),
                () -> assertNotNull(client.regional()),
                () -> assertNotNull(client.me()),
                () -> assertNotNull(client.apiApplications()),
                () -> assertNotNull(client.webhooks()),
                () -> assertNotNull(client.embedded()),
                () -> assertNotNull(client.documents().raw()),
                () -> assertNotNull(client.templates().raw()),
                () -> assertNotNull(client.scriptTag("nonce")),
                () ->
                        assertNotNull(
                                client.signingIframe(
                                        new com.signwell.sdk.embedded.Embedded.IframeOptions(
                                                "https://www.signwell.com/sign/test"))));

        HttpRequest.Builder request =
                HttpRequest.newBuilder(URI.create("https://example.com/me"))
                        .header("X-Api-Key", "wrong")
                        .header("User-Agent", "wrong");
        options.createRawClient().getRequestInterceptor().accept(request);
        assertEquals("key", request.build().headers().firstValue("X-Api-Key").orElseThrow());
        assertEquals(
                SignWellOptions.USER_AGENT,
                request.build().headers().firstValue("User-Agent").orElseThrow());

        Duration customTimeout = Duration.ofSeconds(7);
        SignWellOptions customized =
                SignWellOptions.builder("key")
                        .timeout(Duration.ofSeconds(30))
                        .customizeHttpClient(builder -> builder.connectTimeout(customTimeout))
                        .build();
        ApiClient raw = customized.createRawClient();
        assertSame(raw.getHttpClient(), raw.getHttpClient());
        assertEquals(
                java.net.http.HttpClient.Redirect.NEVER, raw.getHttpClient().followRedirects());
        assertEquals(customTimeout, raw.getHttpClient().connectTimeout().orElseThrow());
        assertEquals(
                java.net.http.HttpClient.Redirect.NEVER,
                customized.createHttpClient().followRedirects());
        assertEquals(customTimeout, customized.createHttpClient().connectTimeout().orElseThrow());
    }

    @Test
    void modelsUseUuidOffsetTimeSnakeCaseConditionalSerializationAndUnknownEnums()
            throws Exception {
        assertEquals(UUID.class, DocumentResponse.class.getDeclaredField("id").getType());
        assertEquals(
                OffsetDateTime.class,
                DocumentResponse.class.getDeclaredField("createdAt").getType());

        ObjectMapper mapper = SignWellOptions.builder("key").build().createObjectMapper();
        String json = mapper.writeValueAsString(new DocumentRequest().testMode(true));
        assertTrue(json.contains("\"test_mode\":true"));
        assertFalse(json.contains("testMode"));
        String defaults = mapper.writeValueAsString(new DocumentRequest());
        assertFalse(defaults.contains("test_mode"));
        assertFalse(defaults.contains("embedded_signing_notifications"));
        assertEquals(
                FieldType.UNKNOWN_DEFAULT_OPEN_API,
                mapper.readValue("\"future_field_type\"", FieldType.class));

        ValidationErrorResponse nested =
                mapper.readValue(
                        "{\"errors\":{\"recipients\":{\"0\":{\"email\":[\"is invalid\"]}}}}",
                        ValidationErrorResponse.class);
        Object recipients = nested.getErrors().get("recipients").value();
        assertInstanceOf(java.util.Map.class, recipients);

        DocumentListResponse liveShape =
                mapper.readValue(
                        "{\"documents\":[{\"test_mode\":true,\"id\":\""
                                + UUID.randomUUID()
                                + "\",\"fields\":[[{\"value\":\"approved\"}]]}],"
                                + "\"current_page\":1,\"total_count\":1,\"total_pages\":1}",
                        DocumentListResponse.class);
        assertEquals(
                "approved",
                liveShape
                        .getDocuments()
                        .get(0)
                        .getFields()
                        .get(0)
                        .get(0)
                        .getValue()
                        .getActualInstance());
    }

    @Test
    void checkboxValuesNormalizeOnlyForCheckboxRequestFields() throws Exception {
        ObjectMapper mapper = SignWellOptions.builder("key").build().createObjectMapper();
        FieldsInnerInner checkbox =
                new FieldsInnerInner()
                        .x(1f)
                        .y(2f)
                        .page(1)
                        .recipientId("1")
                        .type(FieldType.CHECKBOX)
                        .value(new AdditionalFieldsInnerInnerValue(Boolean.TRUE));
        assertTrue(mapper.writeValueAsString(checkbox).contains("\"value\":\"t\""));

        checkbox.value(new AdditionalFieldsInnerInnerValue(" false "));
        assertTrue(mapper.writeValueAsString(checkbox).contains("\"value\":\"f\""));
        checkbox.value(new AdditionalFieldsInnerInnerValue("yes"));
        JsonMappingException failure =
                assertThrows(JsonMappingException.class, () -> mapper.writeValueAsString(checkbox));
        assertInstanceOf(IllegalArgumentException.class, failure.getCause());

        checkbox.type(FieldType.TEXT).value(new AdditionalFieldsInnerInnerValue(Boolean.TRUE));
        assertTrue(mapper.writeValueAsString(checkbox).contains("\"value\":true"));
        assertThrows(
                IllegalArgumentException.class,
                () -> com.signwell.sdk.embedded.Embedded.normalizeCheckbox(BigDecimal.ONE));
    }

    @Test
    void fileAndTemplateSelectorsValidateBeforeTransport() {
        StubDocumentApi raw = new StubDocumentApi();
        DocumentsResource documents = new DocumentsResource(raw, null);
        byte[] pdf = "%PDF-1.7\n%%EOF\n".getBytes(java.nio.charset.StandardCharsets.US_ASCII);
        DocumentRequest valid =
                new DocumentRequest()
                        .files(List.of(new FilesInner().name("agreement.pdf").fileBase64(pdf)));
        raw.created = new DocumentResponse();
        assertSame(raw.created, documents.create(valid).join());

        assertThrows(
                IllegalArgumentException.class,
                () -> documents.create(new DocumentRequest()).join());
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        documents
                                .create(
                                        new DocumentRequest()
                                                .files(
                                                        List.of(
                                                                new FilesInner()
                                                                        .name("bad.pdf")
                                                                        .fileBase64(
                                                                                "%PDF-1.7"
                                                                                        .getBytes()))))
                                .join());
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        documents
                                .create(
                                        new DocumentRequest()
                                                .files(
                                                        List.of(
                                                                new FilesInner()
                                                                        .name("bad.pdf")
                                                                        .fileUrl(
                                                                                "https://example.com/a.pdf")
                                                                        .fileBase64(pdf))))
                                .join());

        DocumentFromTemplateRequest neither = new DocumentFromTemplateRequest();
        assertThrows(IllegalArgumentException.class, () -> documents.createFromTemplate(neither));
        DocumentFromTemplateRequest both =
                new DocumentFromTemplateRequest()
                        .templateId(UUID.randomUUID())
                        .templateIds(List.of(UUID.randomUUID()));
        assertThrows(IllegalArgumentException.class, () -> documents.createFromTemplate(both));
    }

    @Test
    void paginationClampsLimitsPreservesQueriesAndIteratesPagesAndItems() {
        StubDocumentApi raw = new StubDocumentApi();
        raw.pages.add(
                new DocumentListResponse()
                        .documents(List.of(new DocumentResponse().name("one")))
                        .nextPage(2));
        raw.pages.add(
                new DocumentListResponse()
                        .documents(List.of(new DocumentResponse().name("two")))
                        .nextPage(null));
        DocumentsResource documents = new DocumentsResource(raw, null);
        var iterator = documents.pages("status:Completed", 1, 100);
        assertEquals("one", iterator.nextItems().join().get(0).getName());
        assertEquals("two", iterator.nextItems().join().get(0).getName());
        assertTrue(iterator.nextItems().join().isEmpty());
        assertEquals(Arrays.asList(1, 2), raw.requestedPages);
        assertEquals(Arrays.asList(50, 50), raw.requestedLimits);
        assertEquals(Arrays.asList("status:Completed", "status:Completed"), raw.queries);
        assertThrows(IllegalArgumentException.class, () -> documents.pages(" ", 1, 50));
        assertThrows(IllegalArgumentException.class, () -> documents.pages(null, 0, 50));

        StubTemplateApi templateRaw = new StubTemplateApi();
        templateRaw.pages.add(
                new DocumentTemplateListResponse().templates(List.of()).nextPage(null));
        TemplatesResource templates = new TemplatesResource(templateRaw);
        assertNotNull(templates.pages().nextPage().join());
        assertEquals(50, templateRaw.limit);
    }

    @Test
    void paginatorRecoversWhenFetchThrowsSynchronously() {
        int[] attempts = {0};
        AsyncPageIterator<Integer, Integer> iterator =
                new AsyncPageIterator<>(
                        1,
                        page -> {
                            if (attempts[0]++ == 0) throw new IllegalStateException("boom");
                            return CompletableFuture.completedFuture(page);
                        },
                        page -> null,
                        List::of);

        assertThrows(CompletionException.class, () -> iterator.nextPage().join());
        assertEquals(1, iterator.nextPage().join());
    }

    @Test
    void pollingSupportsAliasesTerminalStatusesTimeoutsAndCancellation() {
        StubDocumentApi raw = new StubDocumentApi();
        raw.documents.add(new DocumentResponse().status("Pending"));
        raw.documents.add(new DocumentResponse().status("Completed"));
        DocumentsResource documents = new DocumentsResource(raw, null);
        DocumentResponse complete =
                documents
                        .waitForCompletion(
                                UUID.randomUUID(),
                                Duration.ofMillis(1),
                                Duration.ofSeconds(1),
                                null,
                                List.of("Completed"))
                        .join();
        assertEquals("Completed", complete.getStatus());

        for (String status :
                List.of(
                        "Completed",
                        "Manually completed",
                        "Declined",
                        "Canceled",
                        "Bounced",
                        "Blocked",
                        "Error",
                        "Expired")) {
            raw.documents.add(new DocumentResponse().status(status));
            assertEquals(status, documents.waitForCompletion(UUID.randomUUID()).join().getStatus());
        }

        raw.documents.add(new DocumentResponse().status("Pending"));
        CompletionException timeout =
                assertThrows(
                        CompletionException.class,
                        () ->
                                documents
                                        .waitForCompletion(
                                                UUID.randomUUID(),
                                                Duration.ofMillis(1),
                                                Duration.ofSeconds(1),
                                                1,
                                                List.of("Completed"))
                                        .join());
        assertInstanceOf(WaitForCompletionTimeoutException.class, timeout.getCause());
        assertEquals(
                "Pending",
                ((WaitForCompletionTimeoutException) timeout.getCause())
                        .getLastResponse()
                        .getStatus());

        raw.sent = new DocumentResponse().status("Draft");
        UUID id = UUID.randomUUID();
        assertSame(
                raw.sent, documents.updateDocument(id, new UpdateDocumentAndSendRequest()).join());
        assertEquals(id, raw.sentId);

        raw.pendingGet = new CompletableFuture<>();
        CompletableFuture<DocumentResponse> canceled =
                documents.waitForCompletion(UUID.randomUUID());
        assertTrue(canceled.cancel(true));
        assertTrue(canceled.isCancelled());
    }

    private static final class StubDocumentApi extends DocumentApi {
        private final Queue<DocumentListResponse> pages = new ArrayDeque<>();
        private final Queue<DocumentResponse> documents = new ArrayDeque<>();
        private final List<Integer> requestedPages = new ArrayList<>();
        private final List<Integer> requestedLimits = new ArrayList<>();
        private final List<String> queries = new ArrayList<>();
        private DocumentResponse created;
        private DocumentResponse sent;
        private UUID sentId;
        private CompletableFuture<DocumentResponse> pendingGet;

        StubDocumentApi() {
            super(new ApiClient());
        }

        @Override
        public CompletableFuture<DocumentResponse> createDocument(DocumentRequest request) {
            return CompletableFuture.completedFuture(created);
        }

        @Override
        public CompletableFuture<DocumentListResponse> listDocuments(
                Integer page, Integer limit, String query) {
            requestedPages.add(page);
            requestedLimits.add(limit);
            queries.add(query);
            return CompletableFuture.completedFuture(pages.remove());
        }

        @Override
        public CompletableFuture<DocumentResponse> getDocument(UUID id) {
            if (pendingGet != null) return pendingGet;
            return CompletableFuture.completedFuture(documents.remove());
        }

        @Override
        public CompletableFuture<DocumentResponse> sendDocument(
                UUID id, UpdateDocumentAndSendRequest request) {
            sentId = id;
            return CompletableFuture.completedFuture(sent);
        }
    }

    private static final class StubTemplateApi extends TemplateApi {
        private final Queue<DocumentTemplateListResponse> pages = new ArrayDeque<>();
        private int limit;

        StubTemplateApi() {
            super(new ApiClient());
        }

        @Override
        public CompletableFuture<DocumentTemplateListResponse> listTemplates(
                Integer page, Integer limit, String query) {
            this.limit = limit;
            return CompletableFuture.completedFuture(pages.remove());
        }
    }
}
