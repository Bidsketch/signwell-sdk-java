package com.signwell.examples;

import com.signwell.sdk.SignWellClient;

public final class QuickStart {
    private QuickStart() {}

    public static void main(String[] args) {
        String apiKey = System.getenv("SIGNWELL_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("SIGNWELL_API_KEY must be set.");
        }
        new SignWellClient(apiKey)
                .getMe()
                .thenAccept(System.out::println)
                .join();
    }
}
