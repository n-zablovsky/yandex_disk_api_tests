package com.example.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NegativeTest extends BaseTest {

    @Test
    @DisplayName("GET несуществующего файла (404)")
    void testGetNonExistentFile() {
        Response response = api.getWithParams(
                "/v1/disk/resources", "path", "/no_such_file_99999.txt");

        assertEquals(404, response.getStatusCode());
    }

    @Test
    @DisplayName("DELETE несуществующего ресурса (404)")
    void testDeleteNonExistentResource() {
        Response response = api.deleteWithParams(
                "/v1/disk/resources", "path", "/no_such_resource_99999");

        assertEquals(404, response.getStatusCode());
    }

    @Test
    @DisplayName("POST копирование несуществующего файла (404)")
    void testCopyNonExistentFile() {
        Response response = api.postWithParams(
                "/v1/disk/resources/copy",
                "from", "/no_such_file_99999.txt",
                "path", "/copy_target.txt"
        );

        assertEquals(404, response.getStatusCode());
    }

    @Test
    @DisplayName("PUT создание папки с невалидным именем")
    void testCreateFolderWithInvalidName() {
        Response response = api.putWithParams(
                "/v1/disk/resources", "path", "/invalid<>name|folder");

        int status = response.getStatusCode();
        assertTrue(status == 400 || status == 409 || status == 201,
                "Expected 400, 409 or 201, but got: " + status);

        api.deleteWithParams("/v1/disk/resources", "path", "/invalid<>name|folder");
    }

    @Test
    @DisplayName("GET с невалидным токеном (401)")
    void testInvalidToken() {
        Response response = io.restassured.RestAssured
                .given()
                .header("Authorization", "OAuth invalid_token_12345")
                .contentType("application/json")
                .when()
                .get("https://cloud-api.yandex.net/v1/disk");

        assertEquals(401, response.getStatusCode());
    }
}