package com.example.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileTest extends BaseTest {

    private static final String TEST_FILE = "/test_file_autotest.txt";
    private static final String TEST_FILE_COPY = "/test_file_autotest_copy.txt";
    private static final String UPLOAD_URL = "https://raw.githubusercontent.com/git/git/master/README.md";

    @AfterEach
    void cleanUp() {
        api.deleteWithParams("/v1/disk/resources", "path", TEST_FILE);
        api.deleteWithParams("/v1/disk/resources", "path", TEST_FILE_COPY);
    }

    private void uploadFile(String path) throws InterruptedException {
        Response uploadResponse = api.postWithParams(
                "/v1/disk/resources/upload",
                "url", UPLOAD_URL,
                "path", path
        );
        assertEquals(202, uploadResponse.getStatusCode());

        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            Response check = api.getWithParams("/v1/disk/resources", "path", path);
            if (check.getStatusCode() == 200) {
                return;
            }
        }
        fail("File was not uploaded in 10 seconds");
    }

    @Test
    @DisplayName("POST /v1/disk/resources/upload — загрузка файла по URL")
    void testUploadFileByUrl() throws InterruptedException {
        uploadFile(TEST_FILE);

        Response checkResponse = api.getWithParams(
                "/v1/disk/resources", "path", TEST_FILE);

        assertEquals(200, checkResponse.getStatusCode());
    }

    @Test
    @DisplayName("POST /v1/disk/resources/copy — копирование файла")
    void testCopyFile() throws InterruptedException {
        uploadFile(TEST_FILE);

        Response copyResponse = api.postWithParams(
                "/v1/disk/resources/copy",
                "from", TEST_FILE,
                "path", TEST_FILE_COPY
        );

        assertEquals(201, copyResponse.getStatusCode());
    }
}