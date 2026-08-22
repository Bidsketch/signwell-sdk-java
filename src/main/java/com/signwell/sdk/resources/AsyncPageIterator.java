package com.signwell.sdk.resources;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.IntFunction;

/** Stateful, async-first page and item iterator. Calls must be sequential. */
public final class AsyncPageIterator<P, T> {
    private final IntFunction<CompletableFuture<P>> fetch;
    private final Function<P, Integer> nextPage;
    private final Function<P, List<T>> items;
    private int page;
    private boolean done;
    private boolean inFlight;

    public AsyncPageIterator(
            int firstPage,
            IntFunction<CompletableFuture<P>> fetch,
            Function<P, Integer> nextPage,
            Function<P, List<T>> items) {
        this.page = firstPage;
        this.fetch = fetch;
        this.nextPage = nextPage;
        this.items = items;
    }

    public synchronized CompletableFuture<P> nextPage() {
        if (done) return CompletableFuture.completedFuture(null);
        if (inFlight)
            return CompletableFuture.failedFuture(
                    new IllegalStateException(
                            "Wait for the current page request before requesting another page."));
        inFlight = true;
        try {
            return fetch.apply(page)
                    .whenComplete(
                            (result, failure) -> {
                                synchronized (this) {
                                    inFlight = false;
                                    if (failure == null) {
                                        Integer next = nextPage.apply(result);
                                        done = next == null;
                                        if (next != null) page = next;
                                    }
                                }
                            });
        } catch (RuntimeException failure) {
            inFlight = false;
            return CompletableFuture.failedFuture(failure);
        }
    }

    public CompletableFuture<List<T>> nextItems() {
        return nextPage()
                .thenApply(value -> value == null ? Collections.emptyList() : items.apply(value));
    }
}
