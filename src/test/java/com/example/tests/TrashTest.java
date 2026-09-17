package com.example.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrashTest extends BaseTest {

    private static final String TEST_FILE = "/trash_test_file.txt";
    private static final String UPLOAD_URL = "https://raw.githubusercontent.com/git/git/master/README.md";

    @Test
    @DisplayName("GET /v1/disk/trash/resources — содержимое корзины")
    void testGetTrashContents() {
        Response response = api.get("/v1/disk/trash/resources");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    @DisplayName("DELETE /v1/disk/trash/resources — очистка корзины")
    void testClearTrash() {
        Response response = api.deleteWithParams(
                "/v1/disk/trash/resources",
                "path", "/"
        );

        assertTrue(response.getStatusCode() == 204
                || response.getStatusCode() == 202);
    }

    @Test
    @DisplayName("PUT /v1/disk/resources/restore — восстановление из корзины")
    void testRestoreFromTrash() throws InterruptedException {
        api.postWithParams(
                "/v1/disk/resources/upload",
                "url", UPLOAD_URL,
                "path", TEST_FILE
        );

        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            Response check = api.getWithParams("/v1/disk/resources", "path", TEST_FILE);
            if (check.getStatusCode() == 200) {
                break;
            }
        }

        api.deleteWithParams("/v1/disk/resources", "path", TEST_FILE);

        Thread.sleep(2000);

        Response trashList = api.get("/v1/disk/trash/resources");
        assertEquals(200, trashList.getStatusCode());

        String trashPath = trashList.jsonPath().getString("_embedded.items[0].path");
        assertNotNull(trashPath);

        Response response = api.putWithParams(
                "/v1/disk/resources/restore",
                "path", trashPath
        );

        assertNotEquals(404, response.getStatusCode(),
                "Restore endpoint should exist");

        api.deleteWithParams("/v1/disk/resources", "path", TEST_FILE);
    }
}