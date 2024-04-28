package com.bacen;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@TestInstance(Lifecycle.PER_CLASS)
public class DictResourceTest {

    @BeforeAll
    public static void setup() {
        RestAssured.basePath = "/bacen/dict";
    }

    @Test
    public void testGetEntries() {

        final String[] keys = new String[] {
            "11111111101",
            "22222222201",
            "33333333301",
            "22222222201"
        };

        given()
            .queryParam("keys", (Object[]) keys)
            .get("/entry")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", greaterThan(0));
    }
}
