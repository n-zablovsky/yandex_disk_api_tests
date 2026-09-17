package com.example.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class DiskInfoTest extends BaseTest {

    @Test
    @DisplayName("GET /v1/disk — информация о диске")
    void testGetDiskInfo() {
        Response response = api.get("/v1/disk");

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.jsonPath().getString("total_space"));
        assertNotNull(response.jsonPath().getString("used_space"));
    }

    @Test
    @DisplayName("GET /v1/disk — без токена (401)")
    void testGetDiskInfoWithoutToken() {
        Response response = given()
                .contentType("application/json")
                .when()
                .get("https://cloud-api.yandex.net/v1/disk");

        assertEquals(401, response.getStatusCode());
    }
}