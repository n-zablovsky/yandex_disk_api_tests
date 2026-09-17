package com.example.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PublicResourceTest extends BaseTest {

    private static final String TEST_FILE = "/public_test_file.txt";
    private static final String UPLOAD_URL = "https://raw.githubusercontent.com/git/git/master/README.md";

    @AfterEach
    void cleanUp() {
        api.putWithParams("/v1/disk/resources/unpublish", "path", TEST_FILE);
        api.deleteWithParams("/v1/disk/resources", "path", TEST_FILE);
    }

    private void uploadFile() throws InterruptedException {
        api.postWithParams(
                "/v1/disk/resources/upload",
                "url", UPLOAD_URL,
                "path", TEST_FILE
        );

        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            Response check = api.getWithParams("/v1/disk/resources", "path", TEST_FILE);
            if (check.getStatusCode() == 200) {
                return;
            }
        }
    }

    @Test
    @DisplayName("PUT /v1/disk/resources/publish — публикация файла")
    void testPublishFile() throws InterruptedException {
        uploadFile();

        Response response = api.putWithParams(
                "/v1/disk/resources/publish", "path", TEST_FILE);

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.jsonPath().getString("href"));
    }

    @Test
    @DisplayName("GET /v1/disk/resources/public — список публичных ресурсов")
    void testGetPublicResources() {
        Response response = api.get("/v1/disk/resources/public");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    @DisplayName("PUT /v1/disk/resources/unpublish — снятие публикации")
    void testUnpublishFile() throws InterruptedException {
        uploadFile();

        api.putWithParams("/v1/disk/resources/publish", "path", TEST_FILE);

        Response response = api.putWithParams(
                "/v1/disk/resources/unpublish", "path", TEST_FILE);

        assertEquals(200, response.getStatusCode());
    }
}