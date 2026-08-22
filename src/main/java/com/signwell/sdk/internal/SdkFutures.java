package com.signwell.sdk.internal;

import com.signwell.sdk.errors.SignWellException;
import com.signwell.sdk.raw.ApiException;
import java.util.concurrent.CompletableFuture;

public final class SdkFutures {
    private SdkFutures() {}

    @FunctionalInterface
    public interface ApiCall<T> {
        CompletableFuture<T> run() throws ApiException;
    }

    public static <T> CompletableFuture<T> call(ApiCall<T> call) {
        try {
            return call.run()
                    .handle(
                            (value, failure) -> {
                                if (failure != null) throw ExceptionMapper.map(failure);
                                return value;
                            });
        } catch (Throwable failure) {
            return CompletableFuture.failedFuture(ExceptionMapper.map(failure));
        }
    }

    public static <T> CompletableFuture<T> callRequired(ApiCall<T> call, String emptyMessage) {
        return call(call)
                .thenApply(
                        value -> {
                            if (value == null) throw new SignWellException(emptyMessage);
                            return value;
                        });
    }
}
