package com.signwell.sdk.embedded;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.signwell.sdk.models.*;
import com.signwell.sdk.raw.ApiClient;
import com.signwell.sdk.resources.DocumentsResource;
import java.net.IDN;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

/** Embedded signing/requesting workflows, values, and safe HTML renderers. */
public final class Embedded {
    public static final String SCRIPT_URL = "https://static.signwell.com/assets/embedded.js";
    private static final Pattern HANDLER =
            Pattern.compile(
                    "^[$A-Z_][0-9A-Z_$]*(?:\\.[$A-Z_][0-9A-Z_$]*)*$", Pattern.CASE_INSENSITIVE);
    private static final Set<String> BLOCKED =
            new HashSet<>(Arrays.asList("__proto__", "constructor", "prototype"));
    private final DocumentsResource documents;

    public Embedded(DocumentsResource documents) {
        this.documents = documents;
    }

    public CompletableFuture<DocumentResponse> createSigningDocument(
            String name,
            List<FileInput> files,
            List<Recipient> recipients,
            List<List<Field>> fields,
            boolean withSignaturePage,
            boolean textTags,
            boolean testMode,
            boolean sendNotifications) {
        requireText(name, "Document name is required.");
        List<RecipientsInner> recipientModels = recipients(recipients);
        List<List<FieldsInnerInner>> fieldModels = fields(fields, recipientModels);
        validatePlacement(recipientModels, fieldModels, withSignaturePage, textTags);
        DocumentRequest request =
                new DocumentRequest()
                        .name(name)
                        .files(files(files))
                        .recipients(recipientModels)
                        .fields(fieldModels)
                        .withSignaturePage(withSignaturePage)
                        .textTags(textTags)
                        .testMode(testMode)
                        .embeddedSigning(true)
                        .embeddedSigningNotifications(sendNotifications);
        return documents.create(request);
    }

    public CompletableFuture<DocumentResponse> createRequestingDocument(
            String name, List<FileInput> files, List<Recipient> recipients, boolean testMode) {
        requireText(name, "Document name is required.");
        return documents.create(
                new DocumentRequest()
                        .name(name)
                        .files(files(files))
                        .recipients(recipients(recipients))
                        .draft(true)
                        .testMode(testMode));
    }

    public CompletableFuture<DocumentFromTemplateResponse> createSigningDocumentFromTemplate(
            TemplateSelector selector,
            List<TemplateRecipient> recipients,
            boolean testMode,
            boolean sendNotifications) {
        Objects.requireNonNull(selector, "selector").validate();
        DocumentFromTemplateRequest request =
                new DocumentFromTemplateRequest()
                        .recipients(templateRecipients(recipients))
                        .templateId(selector.templateId)
                        .templateIds(selector.templateIds)
                        .testMode(testMode)
                        .embeddedSigning(true)
                        .embeddedSigningNotifications(sendNotifications);
        return documents.createFromTemplate(request);
    }

    public static String signingUrl(DocumentResponse response, int index) {
        List<DocumentResponseRecipientsInner> recipients =
                response == null ? null : response.getRecipients();
        return recipients == null || index < 0 || index >= recipients.size()
                ? null
                : recipients.get(index).getEmbeddedSigningUrl();
    }

    public static Map<String, String> signingUrls(DocumentResponse response) {
        Map<String, String> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        if (response != null && response.getRecipients() != null)
            for (DocumentResponseRecipientsInner recipient : response.getRecipients())
                if (text(recipient.getEmail()) && text(recipient.getEmbeddedSigningUrl()))
                    result.put(recipient.getEmail(), recipient.getEmbeddedSigningUrl());
        return Collections.unmodifiableMap(result);
    }

    public static String signingUrl(DocumentFromTemplateResponse response, int index) {
        List<DocumentFromTemplateResponseRecipientsInner> recipients =
                response == null ? null : response.getRecipients();
        return recipients == null || index < 0 || index >= recipients.size()
                ? null
                : recipients.get(index).getEmbeddedSigningUrl();
    }

    public static Map<String, String> signingUrls(DocumentFromTemplateResponse response) {
        Map<String, String> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        if (response != null && response.getRecipients() != null)
            for (DocumentFromTemplateResponseRecipientsInner recipient : response.getRecipients())
                if (text(recipient.getEmail()) && text(recipient.getEmbeddedSigningUrl()))
                    result.put(recipient.getEmail(), recipient.getEmbeddedSigningUrl());
        return Collections.unmodifiableMap(result);
    }

    public static String normalizeCheckbox(Object value) {
        if (value instanceof Boolean) return (Boolean) value ? "t" : "f";
        if (value instanceof AdditionalFieldsInnerInnerValue)
            value = ((AdditionalFieldsInnerInnerValue) value).getActualInstance();
        if (value instanceof String) {
            String normalized = ((String) value).trim().toLowerCase(Locale.ROOT);
            if (normalized.equals("true") || normalized.equals("t")) return "t";
            if (normalized.equals("false") || normalized.equals("f")) return "f";
        }
        throw new IllegalArgumentException(
                "Checkbox values must be true, false, \"true\", \"false\", \"t\", or \"f\".");
    }

    public static String scriptTag(String nonce) {
        return "<script src=\""
                + SCRIPT_URL
                + "\""
                + (text(nonce) ? " nonce=\"" + html(nonce) + "\"" : "")
                + "></script>";
    }

    public static String signingIframe(IframeOptions options) {
        return render(options, false);
    }

    public static String requestingIframe(IframeOptions options) {
        return render(options, true);
    }

    private static String render(IframeOptions options, boolean requesting) {
        Objects.requireNonNull(options, "options");
        Map<String, Object> config = new LinkedHashMap<>();
        config.put("url", validateUrl(options.url, options.allowedEmbedHosts, "Embed URL"));
        put(config, "containerId", options.containerId);
        put(config, "allowClose", options.allowClose);
        put(config, "showHeader", options.showHeader);
        put(config, "allowDownload", options.allowDownload);
        put(
                config,
                requesting ? "showSendButton" : "allowDecline",
                requesting ? options.showSendButton : options.allowDecline);
        if (options.redirectUrl != null)
            config.put(
                    "redirectUrl",
                    validateUrl(options.redirectUrl, options.allowedRedirectHosts, "Redirect URL"));
        if (options.declineRedirectUrl != null)
            config.put(
                    "declineRedirectUrl",
                    validateUrl(
                            options.declineRedirectUrl,
                            options.allowedRedirectHosts,
                            "Decline redirect URL"));
        Map<String, String> events = normalizeEvents(options.events);
        String nonce = text(options.nonce) ? " nonce=\"" + html(options.nonce) + "\"" : "";
        return "<script"
                + nonce
                + ">(function(){var config="
                + json(config)
                + ";var eventPaths="
                + json(events)
                + ";var resolveSignWellHandler=function(path){if(!path)return null;return"
                + " path.split('.').reduce(function(context,key){return"
                + " context&&context[key];},globalThis);};if(Object.keys(eventPaths).length>0){config.events={};Object.keys(eventPaths).forEach(function(name){var"
                + " handler=resolveSignWellHandler(eventPaths[name]);if(typeof"
                + " handler==='function')config.events[name]=handler;});}var embed=new"
                + " SignWellEmbed(config);"
                + (options.autoOpen ? "embed.open();" : "")
                + "})();</script>";
    }

    private static List<FilesInner> files(List<FileInput> inputs) {
        if (inputs == null || inputs.isEmpty())
            throw new IllegalArgumentException("At least one file is required.");
        List<FilesInner> result = new ArrayList<>();
        for (FileInput input : inputs) {
            requireText(input.name, "Every file requires a name.");
            boolean hasUrl = text(input.fileUrl);
            boolean hasBase64 = text(input.fileBase64);
            if (hasUrl == hasBase64)
                throw new IllegalArgumentException(
                        "Every file must contain exactly one of fileUrl or fileBase64.");
            FilesInner model = new FilesInner().name(input.name);
            if (hasUrl) {
                validateHttps(input.fileUrl, "File URL");
                model.fileUrl(input.fileUrl);
            } else {
                String compact = input.fileBase64.replaceAll("\\s", "");
                byte[] bytes;
                try {
                    bytes = Base64.getDecoder().decode(compact);
                } catch (IllegalArgumentException failure) {
                    throw new IllegalArgumentException(
                            "fileBase64 must contain valid RFC 4648 base64.", failure);
                }
                if (input.name.toLowerCase(Locale.ROOT).endsWith(".pdf")) validatePdf(bytes);
                model.fileBase64(bytes);
            }
            result.add(model);
        }
        return result;
    }

    private static List<RecipientsInner> recipients(List<Recipient> inputs) {
        if (inputs == null || inputs.isEmpty())
            throw new IllegalArgumentException("At least one recipient is required.");
        List<RecipientsInner> result = new ArrayList<>();
        for (int i = 0; i < inputs.size(); i++) {
            Recipient input = inputs.get(i);
            requireText(input.name, "Recipient name is required.");
            requireText(input.email, "Recipient email is required.");
            result.add(
                    new RecipientsInner()
                            .id(text(input.id) ? input.id : Integer.toString(i + 1))
                            .name(input.name)
                            .email(input.email)
                            .passcode(passcode(input.passcode)));
        }
        return result;
    }

    private static List<TemplateRecipientsInner> templateRecipients(
            List<TemplateRecipient> inputs) {
        if (inputs == null || inputs.isEmpty())
            throw new IllegalArgumentException("At least one recipient is required.");
        List<TemplateRecipientsInner> result = new ArrayList<>();
        for (int i = 0; i < inputs.size(); i++) {
            TemplateRecipient input = inputs.get(i);
            requireText(input.name, "Recipient name is required.");
            requireText(input.email, "Recipient email is required.");
            result.add(
                    new TemplateRecipientsInner()
                            .id(text(input.id) ? input.id : Integer.toString(i + 1))
                            .name(input.name)
                            .email(input.email)
                            .placeholderName(input.placeholderName)
                            .passcode(passcode(input.passcode)));
        }
        return result;
    }

    private static List<List<FieldsInnerInner>> fields(
            List<List<Field>> inputs, List<RecipientsInner> recipients) {
        if (inputs == null) return null;
        String defaultId = recipients.isEmpty() ? null : recipients.get(0).getId();
        List<List<FieldsInnerInner>> result = new ArrayList<>();
        for (List<Field> fileFields : inputs) {
            List<FieldsInnerInner> models = new ArrayList<>();
            for (Field input : fileFields) {
                String recipientId = text(input.recipientId) ? input.recipientId : defaultId;
                requireText(recipientId, "Every field must identify a recipient.");
                models.add(
                        new FieldsInnerInner()
                                .x(input.x)
                                .y(input.y)
                                .page(input.page)
                                .recipientId(recipientId)
                                .type(input.type)
                                .required(input.required)
                                .label(input.label));
            }
            result.add(models);
        }
        return result;
    }

    private static void validatePlacement(
            List<RecipientsInner> recipients,
            List<List<FieldsInnerInner>> fields,
            boolean withSignaturePage,
            boolean textTags) {
        if (withSignaturePage || textTags) return;
        Set<String> assigned = new HashSet<>();
        if (fields != null)
            for (List<FieldsInnerInner> file : fields)
                for (FieldsInnerInner field : file) assigned.add(field.getRecipientId());
        for (RecipientsInner recipient : recipients)
            if (!assigned.contains(recipient.getId()))
                throw new IllegalArgumentException(
                        "Embedded signing requires fields for every recipient, signature-page mode,"
                                + " or text-tag mode.");
    }

    private static void validatePdf(byte[] bytes) {
        byte[] header = "%PDF-".getBytes(StandardCharsets.US_ASCII);
        if (bytes.length < header.length
                || !Arrays.equals(header, Arrays.copyOf(bytes, header.length)))
            throw new IllegalArgumentException("PDF content is missing its PDF header.");
        int end = bytes.length - 1;
        while (end >= 0 && (bytes[end] == 0 || Character.isWhitespace((char) bytes[end]))) end--;
        byte[] trailer = "%%EOF".getBytes(StandardCharsets.US_ASCII);
        if (end + 1 < trailer.length
                || !Arrays.equals(
                        trailer, Arrays.copyOfRange(bytes, end + 1 - trailer.length, end + 1)))
            throw new IllegalArgumentException(
                    "PDF content is incomplete or missing its EOF trailer.");
    }

    private static Map<String, String> normalizeEvents(Map<String, String> input) {
        Map<String, String> result = new LinkedHashMap<>();
        if (input == null) return result;
        input.forEach(
                (name, raw) -> {
                    String path = raw == null ? "" : raw.trim();
                    if (!HANDLER.matcher(path).matches())
                        throw new IllegalArgumentException(
                                "Event handlers must be safe dotted JavaScript identifier paths.");
                    for (String segment : path.split("\\."))
                        if (BLOCKED.contains(segment))
                            throw new IllegalArgumentException(
                                    "Event handler contains a blocked JavaScript property.");
                    result.put(name, path);
                });
        return result;
    }

    private static String validateUrl(String raw, Collection<String> customHosts, String label) {
        URI uri = validateHttps(raw, label);
        String host = IDN.toASCII(uri.getHost()).toLowerCase(Locale.ROOT);
        boolean signwell = host.equals("signwell.com") || host.endsWith(".signwell.com");
        Set<String> allowed = new HashSet<>();
        if (customHosts != null)
            for (String value : customHosts) {
                String exact = value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
                if (exact.isEmpty()
                        || exact.contains("/")
                        || exact.contains(":")
                        || exact.contains("@"))
                    throw new IllegalArgumentException("Allowed hosts must be exact hostnames.");
                allowed.add(IDN.toASCII(exact));
            }
        if (!signwell && !allowed.contains(host))
            throw new IllegalArgumentException(label + " host is not allowed.");
        return uri.toASCIIString();
    }

    private static URI validateHttps(String raw, String label) {
        try {
            URI uri = URI.create(raw);
            if (!uri.isAbsolute()
                    || !"https".equalsIgnoreCase(uri.getScheme())
                    || uri.getHost() == null
                    || uri.getUserInfo() != null)
                throw new IllegalArgumentException(
                        label + " must be a credential-free absolute HTTPS URL.");
            return uri;
        } catch (RuntimeException failure) {
            if (failure instanceof IllegalArgumentException
                    && failure.getMessage().startsWith(label)) throw failure;
            throw new IllegalArgumentException(
                    label + " must be a credential-free absolute HTTPS URL.", failure);
        }
    }

    private static String json(Object value) {
        try {
            return ApiClient.createDefaultObjectMapper()
                    .writeValueAsString(value)
                    .replace("<", "\\u003c")
                    .replace(">", "\\u003e")
                    .replace("&", "\\u0026")
                    .replace("\u2028", "\\u2028")
                    .replace("\u2029", "\\u2029");
        } catch (JsonProcessingException failure) {
            throw new IllegalStateException(failure);
        }
    }

    private static String html(String value) {
        return value.replace("&", "&amp;")
                .replace("\"", "&quot;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private static String passcode(Object value) {
        return value == null || value.toString().trim().isEmpty() ? null : value.toString().trim();
    }

    private static boolean text(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private static void requireText(String value, String message) {
        if (!text(value)) throw new IllegalArgumentException(message);
    }

    private static void put(Map<String, Object> target, String key, Object value) {
        if (value != null) target.put(key, value);
    }

    public static final class Recipient {
        public final String name, email, id;
        public final Object passcode;

        public Recipient(String name, String email) {
            this(name, email, null, null);
        }

        public Recipient(String name, String email, String id, Object passcode) {
            this.name = name;
            this.email = email;
            this.id = id;
            this.passcode = passcode;
        }
    }

    public static final class TemplateRecipient {
        public final String name, email, placeholderName, id;
        public final Object passcode;

        public TemplateRecipient(
                String name, String email, String placeholderName, String id, Object passcode) {
            this.name = name;
            this.email = email;
            this.placeholderName = placeholderName;
            this.id = id;
            this.passcode = passcode;
        }
    }

    public static final class FileInput {
        public final String name, fileUrl, fileBase64;

        public FileInput(String name, String fileUrl, String fileBase64) {
            this.name = name;
            this.fileUrl = fileUrl;
            this.fileBase64 = fileBase64;
        }

        public static FileInput url(String name, String url) {
            return new FileInput(name, url, null);
        }

        public static FileInput base64(String name, String value) {
            return new FileInput(name, null, value);
        }
    }

    public static final class Field {
        public final float x, y;
        public final int page;
        public final FieldType type;
        public final String recipientId, label;
        public final boolean required;

        public Field(
                float x,
                float y,
                int page,
                FieldType type,
                String recipientId,
                boolean required,
                String label) {
            this.x = x;
            this.y = y;
            this.page = page;
            this.type = Objects.requireNonNull(type, "type");
            this.recipientId = recipientId;
            this.required = required;
            this.label = label;
        }
    }

    public static final class TemplateSelector {
        private final UUID templateId;
        private final List<UUID> templateIds;

        private TemplateSelector(UUID id, List<UUID> ids) {
            templateId = id;
            templateIds = ids;
        }

        public static TemplateSelector one(UUID id) {
            return new TemplateSelector(Objects.requireNonNull(id, "id"), null);
        }

        public static TemplateSelector many(List<UUID> ids) {
            return new TemplateSelector(null, ids == null ? null : new ArrayList<>(ids));
        }

        private void validate() {
            if ((templateId != null) == (templateIds != null && !templateIds.isEmpty()))
                throw new IllegalArgumentException(
                        "Provide exactly one template ID or one non-empty collection of template"
                                + " IDs.");
        }
    }

    public static final class IframeOptions {
        public final String url;
        public Collection<String> allowedEmbedHosts, allowedRedirectHosts;
        public String containerId, redirectUrl, declineRedirectUrl, nonce;
        public Boolean allowDecline, allowClose, showHeader, allowDownload, showSendButton;
        public Map<String, String> events;
        public boolean autoOpen = true;

        public IframeOptions(String url) {
            this.url = url;
        }

        public IframeOptions allowedEmbedHosts(Collection<String> value) {
            allowedEmbedHosts = value;
            return this;
        }

        public IframeOptions allowedRedirectHosts(Collection<String> value) {
            allowedRedirectHosts = value;
            return this;
        }

        public IframeOptions containerId(String value) {
            containerId = value;
            return this;
        }

        public IframeOptions redirectUrl(String value) {
            redirectUrl = value;
            return this;
        }

        public IframeOptions declineRedirectUrl(String value) {
            declineRedirectUrl = value;
            return this;
        }

        public IframeOptions nonce(String value) {
            nonce = value;
            return this;
        }

        public IframeOptions events(Map<String, String> value) {
            events = value;
            return this;
        }

        public IframeOptions autoOpen(boolean value) {
            autoOpen = value;
            return this;
        }

        public IframeOptions allowDecline(boolean value) {
            allowDecline = value;
            return this;
        }

        public IframeOptions allowClose(boolean value) {
            allowClose = value;
            return this;
        }

        public IframeOptions showHeader(boolean value) {
            showHeader = value;
            return this;
        }

        public IframeOptions allowDownload(boolean value) {
            allowDownload = value;
            return this;
        }

        public IframeOptions showSendButton(boolean value) {
            showSendButton = value;
            return this;
        }
    }
}
