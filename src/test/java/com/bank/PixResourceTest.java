package com.bank;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.json.Json;
import jakarta.json.JsonArray;

@TestInstance(Lifecycle.PER_CLASS)
public class PixResourceTest {
    
    @BeforeAll
    public static void setup() {
        RestAssured.basePath = "/bank/pix";
    }    

    @Test
    public void testCreatePixRequests() {

        final JsonArray json = Json.createArrayBuilder()
                .add(
                    Json.createObjectBuilder()
                    .add("pixKey", "11111111101")
                    .add("value", 50.0)
                    .build()
                )
                .add(
                    Json.createObjectBuilder()
                    .add("pixKey", "22222222201")
                    .add("value", 50.0)
                    .build()
                )
                .add(
                    Json.createObjectBuilder()
                    .add("pixKey", "33333333301")
                    .add("value", 50.0)
                    .build()
                )
                .add(
                    Json.createObjectBuilder()
                    .add("pixKey", "22222222201")
                    .add("value", 50.0)
                    .build()
                )
                .build();
        
        given()
            .contentType(ContentType.JSON)
            .body(json.toString())
            .post("/create-pix-request")
        .then()
            .statusCode(200);
    }

    @Test
    public void testConsultPixRequests() {
        
        given()
            .contentType(ContentType.JSON)
            .get("/consult-pix-requests")
        .then()
            .statusCode(200);
    }

    @Test
    public void testConsultPixUpdates() {

        given()
            .contentType(ContentType.JSON)
            .get("/consult-updated-pixes")
        .then()
            .statusCode(200);
    }
}
