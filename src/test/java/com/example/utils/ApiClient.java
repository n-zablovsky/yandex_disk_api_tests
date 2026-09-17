package com.example.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiClient {

    private static final String BASE_URL = "https://cloud-api.yandex.net";
    private final String token;

    public ApiClient(String token) {
        this.token = token;
        RestAssured.baseURI = BASE_URL;
    }

    private RequestSpecification request() {
        return given()
                .header("Authorization", "OAuth " + token)
                .contentType("application/json");
    }

    public Response get(String path) {
        return request().when().get(path);
    }

    public Response getWithParams(String path, String key, String value) {
        return request()
                .queryParam(key, value)
                .when()
                .get(path);
    }

    public Response put(String path) {
        return request().when().put(path);
    }

    public Response putWithParams(String path, String key, String value) {
        return request()
                .queryParam(key, value)
                .when()
                .put(path);
    }

    public Response post(String path) {
        return request().when().post(path);
    }

    public Response postWithParams(String path, String key1, String value1, String key2, String value2) {
        return request()
                .queryParam(key1, value1)
                .queryParam(key2, value2)
                .when()
                .post(path);
    }

    public Response delete(String path) {
        return request().when().delete(path);
    }

    public Response deleteWithParams(String path, String key, String value) {
        return request()
                .queryParam(key, value)
                .when()
                .delete(path);
    }
}