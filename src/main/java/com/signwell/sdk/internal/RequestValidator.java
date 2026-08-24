package com.signwell.sdk.internal;

import com.signwell.sdk.models.AdditionalFilesInner;
import com.signwell.sdk.models.DocumentFromTemplateRequest;
import com.signwell.sdk.models.DocumentRequest;
import com.signwell.sdk.models.DocumentTemplateRequest;
import com.signwell.sdk.models.FilesInner;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

/** Local request-shape validation shared by all convenience resources. */
public final class RequestValidator {
    private static final byte[] PDF_HEADER = "%PDF-".getBytes(StandardCharsets.US_ASCII);
    private static final byte[] PDF_TRAILER = "%%EOF".getBytes(StandardCharsets.US_ASCII);

    private RequestValidator() {}

    public static void validate(DocumentRequest request) {
        if (request == null) throw new NullPointerException("request");
        validateFiles(request.getFiles(), true);
    }

    public static void validate(DocumentTemplateRequest request) {
        if (request == null) throw new NullPointerException("request");
        validateFiles(request.getFiles(), true);
    }

    public static void validate(DocumentFromTemplateRequest request) {
        if (request == null) throw new NullPointerException("request");
        boolean hasOne = request.getTemplateId() != null;
        boolean hasMany = request.getTemplateIds() != null && !request.getTemplateIds().isEmpty();
        if (hasOne == hasMany) {
            throw new IllegalArgumentException(
                    "Provide exactly one template ID or one non-empty collection of template IDs.");
        }
        if (hasMany && request.getTemplateIds().stream().anyMatch(id -> id == null)) {
            throw new IllegalArgumentException("Template IDs cannot contain null values.");
        }
        Collection<AdditionalFilesInner> files = request.getFiles();
        if (files != null) {
            for (AdditionalFilesInner file : files) {
                if (file == null)
                    throw new IllegalArgumentException("Files cannot contain null values.");
                validateFile(file.getName(), file.getFileUrl(), file.getFileBase64());
            }
        }
    }

    private static void validateFiles(Collection<FilesInner> files, boolean required) {
        if (files == null || files.isEmpty()) {
            if (required) throw new IllegalArgumentException("At least one file is required.");
            return;
        }
        for (FilesInner file : files) {
            if (file == null)
                throw new IllegalArgumentException("Files cannot contain null values.");
            validateFile(file.getName(), file.getFileUrl(), file.getFileBase64());
        }
    }

    private static void validateFile(String name, String url, byte[] bytes) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Every file requires a nonblank name.");
        }
        boolean hasUrl = url != null && !url.trim().isEmpty();
        boolean hasBytes = bytes != null && bytes.length > 0;
        if (hasUrl == hasBytes) {
            throw new IllegalArgumentException(
                    "Every file must contain exactly one of fileUrl or fileBase64.");
        }
        if (hasUrl) validateHttps(url, "File URL");
        if (hasBytes && name.toLowerCase(Locale.ROOT).endsWith(".pdf")) validatePdf(bytes);
    }

    private static void validateHttps(String raw, String label) {
        try {
            URI uri = URI.create(raw);
            if (!uri.isAbsolute()
                    || !"https".equalsIgnoreCase(uri.getScheme())
                    || uri.getHost() == null
                    || uri.getUserInfo() != null) {
                throw new IllegalArgumentException();
            }
        } catch (RuntimeException failure) {
            throw new IllegalArgumentException(
                    label + " must be a credential-free absolute HTTPS URL.", failure);
        }
    }

    private static void validatePdf(byte[] bytes) {
        if (bytes.length < PDF_HEADER.length
                || !Arrays.equals(PDF_HEADER, Arrays.copyOf(bytes, PDF_HEADER.length))) {
            throw new IllegalArgumentException("PDF content is missing its PDF header.");
        }
        int end = bytes.length - 1;
        while (end >= 0 && (bytes[end] == 0 || Character.isWhitespace((char) bytes[end]))) end--;
        if (end + 1 < PDF_TRAILER.length
                || !Arrays.equals(
                        PDF_TRAILER,
                        Arrays.copyOfRange(bytes, end + 1 - PDF_TRAILER.length, end + 1))) {
            throw new IllegalArgumentException(
                    "PDF content is incomplete or missing its EOF trailer.");
        }
    }
}
