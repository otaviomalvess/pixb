package com.bacen;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class DirectoryResourceTest {

    // @Test
    // public void testGetEntry() {
    //     final String pkey = "mail@mail.com";

    //     given()
    //         .get("/api/entry/{key}", pkey)
    //     .then()
    //         .statusCode(200)
    //         .contentType(ContentType.JSON)
    //         .body("pkey", equalTo(pkey))
    //         .body("pkey_type", equalTo(1))
    //         .body("owner", equalTo("11111111101"));
    // }

}
