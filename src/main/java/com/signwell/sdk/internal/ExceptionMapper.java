package com.signwell.sdk.internal;

import com.signwell.sdk.errors.*;
import com.signwell.sdk.raw.ApiException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.net.http.HttpTimeoutException;
import java.nio.channels.UnresolvedAddressException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import javax.net.ssl.SSLException;

public final class ExceptionMapper {
    private ExceptionMapper() {}

    public static RuntimeException map(Throwable failure) {
        Throwable cause = unwrap(failure);
        if (cause instanceof SignWellException) return (SignWellException) cause;
        if (cause instanceof ApiException) {
            ApiException api = (ApiException) cause;
            if (api.getCode() <= 0) {
                return new SignWellException(
                        "SignWell could not serialize or parse the API payload.", api);
            }
            switch (api.getCode()) {
                case 400:
                    return new BadRequestException(api);
                case 401:
                    return new AuthenticationException(api);
                case 403:
                    return new PermissionDeniedException(api);
                case 404:
                    return new NotFoundException(api);
                case 409:
                    return new ConflictException(api);
                case 422:
                    return new UnprocessableEntityException(api);
                case 429:
                    return new RateLimitException(api);
                default:
                    if (api.getCode() >= 500) return new InternalServerException(api);
                    return new ApiStatusException(
                            api.getCode(), api.getResponseBody(), api.getResponseHeaders(), api);
            }
        }
        if (cause instanceof HttpTimeoutException) return new ApiTimeoutException(cause);
        if (cause instanceof ConnectException
                || cause instanceof UnknownHostException
                || cause instanceof UnresolvedAddressException
                || cause instanceof SSLException
                || cause instanceof IOException) return new ApiConnectionException(cause);
        return new SignWellException("The SignWell SDK request failed.", cause);
    }

    private static Throwable unwrap(Throwable failure) {
        Throwable current = failure;
        while ((current instanceof CompletionException || current instanceof ExecutionException)
                && current.getCause() != null) {
            current = current.getCause();
        }
        return current;
    }
}
