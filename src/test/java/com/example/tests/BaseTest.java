package com.example.tests;

import com.example.utils.ApiClient;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static ApiClient api;

    @BeforeAll
    public static void setUp() {
        String token = System.getenv("YANDEX_DISK_TOKEN");
        if (token == null || token.isEmpty()) {
            throw new IllegalStateException("YANDEX_DISK_TOKEN is not set");
        }
        api = new ApiClient(token);
    }
}