package com.example.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ParameterizedPathTest extends BaseTest {

    private String createdFolder;

    @AfterEach
    void cleanUp() {
        if (createdFolder != null) {
            api.deleteWithParams("/v1/disk/resources", "path", createdFolder);
            createdFolder = null;
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/test_folder_eng",
            "/test_folder_with_underscore",
            "/test-folder-with-dash",
            "/test_folder_123"
    })
    void testCreateFolderWithDifferentPaths(String path) {
        createdFolder = path;

        Response response = api.putWithParams(
                "/v1/disk/resources", "path", path);

        assertEquals(201, response.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/test_folder_eng",
            "/test_folder_123"
    })
    void testGetMetadataAfterCreate(String path) {
        createdFolder = path;

        api.putWithParams("/v1/disk/resources", "path", path);

        Response response = api.getWithParams(
                "/v1/disk/resources", "path", path);

        assertEquals(200, response.getStatusCode());
        assertEquals("dir", response.jsonPath().getString("type"));
    }
}