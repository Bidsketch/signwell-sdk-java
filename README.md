# SignWell Java SDK

The official Java SDK for the [SignWell API](https://www.signwell.com/developers). It provides async-first generated API clients plus typed resources, errors, downloads, pagination, polling, embedded workflows, and webhook verification.

## Install

Java 11 or newer is required.

Maven:

```xml
<dependency>
  <groupId>com.signwell</groupId>
  <artifactId>signwell-sdk</artifactId>
  <version>0.1.0-beta.1</version>
</dependency>
```

Gradle:

```groovy
implementation "com.signwell:signwell-sdk:0.1.0-beta.1"
```

## Quick Start

```java
import com.signwell.sdk.SignWellClient;
import com.signwell.sdk.models.DocumentRequest;
import com.signwell.sdk.models.FieldType;
import com.signwell.sdk.models.FieldsInnerInner;
import com.signwell.sdk.models.FilesInner;
import com.signwell.sdk.models.RecipientsInner;
import java.util.List;

SignWellClient signwell = new SignWellClient(System.getenv("SIGNWELL_API_KEY"));

DocumentRequest request = new DocumentRequest()
    .testMode(true)
    .name("NDA")
    .files(List.of(
        new FilesInner()
            .name("nda.pdf")
            .fileUrl("https://example.com/nda.pdf")))
    .recipients(List.of(
        new RecipientsInner()
            .id("1")
            .name("Jane Doe")
            .email("jane@example.com")))
    .fields(List.of(List.of(
        new FieldsInnerInner()
            .page(1)
            .x(120f)
            .y(240f)
            .type(FieldType.SIGNATURE)
            .recipientId("1"))));

signwell.createDocument(request)
    .thenAccept(document -> System.out.println(document.getId()))
    .join();
```

## Resources, Models, Raw Clients, and Errors

Public packages are organized under:

- `com.signwell.sdk`
- `com.signwell.sdk.models`
- `com.signwell.sdk.resources`
- `com.signwell.sdk.errors`
- `com.signwell.sdk.embedded`
- `com.signwell.sdk.webhooks`
- `com.signwell.sdk.raw`

Every resource exposes its generated raw client for response headers and advanced handling:

```java
signwell.documents().raw()
    .getDocumentWithHttpInfo(documentId)
    .thenAccept(response -> System.out.println(response.getHeaders()))
    .join();
```

Convenience methods complete exceptionally with typed `SignWellException` subclasses. `CompletableFuture.join()` wraps them in `CompletionException`:

```java
try {
    signwell.documents().get(documentId).join();
} catch (java.util.concurrent.CompletionException failure) {
    if (failure.getCause() instanceof com.signwell.sdk.errors.NotFoundException) {
        // Document was not found.
    }
}
```

HTTP 403 failures are `PermissionDeniedException` and remain catchable as `ForbiddenException`. Connection and timeout failures are `ApiConnectionException` and `ApiTimeoutException`, both compatible with `TransportException`. Status errors expose the status, parsed JSON/body, response headers, and rate-limit metadata without putting sensitive response data in exception messages.

## Convenience Workflows

`SignWellClient` provides thin async wrappers while retaining all 28 raw operations:

```java
signwell.getMe();
signwell.createDocument(request);
signwell.createDocumentFromTemplate(templateRequest);
signwell.sendDocument(documentId, sendRequest);
signwell.updateDocument(documentId, sendRequest); // alias for sendDocument
signwell.sendReminder(documentId);
signwell.createTemplate(templateRequest);
signwell.updateTemplate(templateId, updateRequest);
signwell.createBulkSend(bulkSendRequest);
signwell.validateBulkSendCsv(csvRequest);
```

## Pagination and Polling

Document and template list helpers preserve raw `key:value` filters, reject blank queries, clamp limits to 50, and start at page 1 by default:

```java
var pages = signwell.documents().pages("status:Completed", 1, 50);
for (;;) {
    var page = pages.nextPage().join();
    if (page == null) break;
    page.getDocuments().forEach(document -> System.out.println(document.getId()));
}

var completed = signwell.documents().waitForCompletion(documentId).join();
```

Polling defaults to a two-second interval and a two-minute timeout. An overload accepts maximum attempts and custom terminal statuses; timeouts raise `WaitForCompletionTimeoutException` with the last response.

## Binary and JSON Response Modes

Explicit methods avoid ambiguous union return types and stream downloads without buffering:

```java
try (var pdf = signwell.getCompletedPdfStream(documentId).join()) {
    pdf.getStream().transferTo(outputStream);
}

var pdfUrl = signwell.getCompletedPdfUrl(documentId).join();
var csv = signwell.bulkSends().csvStream(List.of(templateId)).join();
var csvBase64 = signwell.bulkSends().csvBase64(List.of(templateId)).join();
var nom151 = signwell.regional().nom151Stream(documentId).join();
var nom151Url = signwell.regional().nom151Url(documentId).join();
var nom151Object = signwell.regional().nom151Object(documentId).join();
```

Always close `OwnedResponseStream` with try-with-resources.

Use these explicit methods for the three multi-representation endpoints. Their generated raw
signatures describe only one OpenAPI response schema and cannot safely represent every binary and
JSON mode. `OwnedResponseStream` retains the HTTP status and headers when response metadata is
needed.

## Embedded Helpers

```java
import com.signwell.sdk.embedded.Embedded;
import java.util.List;
import java.util.Map;

var document = signwell.createSigningDocument(
    "NDA",
    List.of(Embedded.FileInput.url("nda.pdf", "https://example.com/nda.pdf")),
    List.of(new Embedded.Recipient("Jane Doe", "jane@example.com")),
    List.of(List.of(new Embedded.Field(120, 240, 1, FieldType.SIGNATURE, null, true, null))),
    false,
    false,
    true,
    false
).join();

String signingUrl = Embedded.signingUrl(document, 0);
String loader = Embedded.scriptTag("your-csp-nonce");
String iframe = Embedded.signingIframe(
    new Embedded.IframeOptions(signingUrl)
        .nonce("your-csp-nonce")
        .events(Map.of("completed", "SignWellHandlers.completed")));
```

Signing helpers require fields for every signer unless signature-page or text-tag mode is enabled. They assign recipient IDs, normalize passcodes and checkbox values, validate URL/base64 file selection and complete PDFs, validate template selection, and safely render script-context JSON. Custom embed and redirect hosts require exact allowlisting.

## Webhook Verification

```java
import com.fasterxml.jackson.databind.JsonNode;
import com.signwell.sdk.webhooks.InMemoryWebhookReplayStore;
import com.signwell.sdk.webhooks.WebhookVerifier;
import java.time.Duration;

JsonNode event = objectMapper.readTree(requestBody).get("event");
var replayStore = new InMemoryWebhookReplayStore();

WebhookVerifier.verifyOnceOrThrow(
    event,
    System.getenv("SIGNWELL_WEBHOOK_ID"),
    replayStore,
    Duration.ofMinutes(5)
).join();
```

`verify`, `verifyOrThrow`, `verifyOnce`, and `verifyOnceOrThrow` use HMAC-SHA256 with constant-time comparison and optional freshness checks. Replay protection always requires freshness. Invalid signatures are never stored and capacity exhaustion fails closed.

The in-memory replay store is only for tests and single-process development. Production webhook handlers must use a shared database or Redis implementation whose insert-if-absent operation is atomic across all application instances.

SignWell's v1 signature authenticates `type@time`, so two otherwise distinct events with the same
type and timestamp have the same replay identity and the second is rejected. Do not add unsigned
payload fields to the replay key; use an idempotent event handler if this boundary matters to your
workflow.

## HTTP and Framework Integration

The SDK uses Java's native `HttpClient`, a 30-second timeout, and no automatic retries. Customize the builder when your application has an idempotency-aware resilience policy:

```java
var options = SignWellOptions.builder(System.getenv("SIGNWELL_API_KEY"))
    .timeout(Duration.ofSeconds(20))
    .customizeHttpClient(builder -> builder.version(java.net.http.HttpClient.Version.HTTP_2))
    .build();
var signwell = new SignWellClient(options);
```

Spring applications can register the framework-neutral client directly:

```java
@Bean
SignWellClient signWellClient(@Value("${signwell.api-key}") String apiKey) {
    return new SignWellClient(apiKey);
}
```

## Build

```bash
mvn -B -ntp spotless:check verify
```

The build compiles for Java 11, runs the parity/security suite, creates the main, sources, and Javadoc JARs, and emits a CycloneDX SBOM. A compile-checked sample is in `examples/quickstart`.

Generated raw clients and models are owned by the private SignWell SDK generator. See [CONTRIBUTING.md](CONTRIBUTING.md) before editing them.

For a destructive test-mode API check, set `SIGNWELL_API_KEY`, install the SDK, and run the
manual harness. It creates and deletes its own documents and templates; optional completed PDF and
NOM-151 fixture IDs enable those download checks.

```bash
mvn -B -ntp install
mvn -B -ntp -f examples/live-acceptance/pom.xml compile \
  org.codehaus.mojo:exec-maven-plugin:3.6.2:java \
  -Dexec.mainClass=com.signwell.examples.LiveAcceptance \
  -Dexec.cleanupDaemonThreads=false
```

## License

MIT
