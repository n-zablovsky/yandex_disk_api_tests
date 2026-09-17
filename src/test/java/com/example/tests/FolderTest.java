package com.example.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FolderTest extends BaseTest {

    private static final String TEST_FOLDER = "/test_folder_autotest";

    @AfterEach
    void cleanUp() {
        api.deleteWithParams("/v1/disk/resources", "path", TEST_FOLDER);
    }

    @Test
    @DisplayName("PUT /v1/disk/resources — создание папки")
    void testCreateFolder() {
        Response response = api.putWithParams(
                "/v1/disk/resources", "path", TEST_FOLDER);

        assertEquals(201, response.getStatusCode());
    }

    @Test
    @DisplayName("GET /v1/disk/resources — метаданные папки")
    void testGetFolderMetadata() {
        api.putWithParams("/v1/disk/resources", "path", TEST_FOLDER);

        Response response = api.getWithParams(
                "/v1/disk/resources", "path", TEST_FOLDER);

        assertEquals(200, response.getStatusCode());
        assertEquals("dir", response.jsonPath().getString("type"));
    }

    @Test
    @DisplayName("DELETE /v1/disk/resources — удаление папки")
    void testDeleteFolder() {
        api.putWithParams("/v1/disk/resources", "path", TEST_FOLDER);

        Response response = api.deleteWithParams(
                "/v1/disk/resources", "path", TEST_FOLDER);

        assertEquals(204, response.getStatusCode());
    }

    @Test
    @DisplayName("GET несуществующей папки (404)")
    void testGetNonExistentFolder() {
        Response response = api.getWithParams(
                "/v1/disk/resources", "path", "/no_such_folder_12345");

        assertEquals(404, response.getStatusCode());
    }
}