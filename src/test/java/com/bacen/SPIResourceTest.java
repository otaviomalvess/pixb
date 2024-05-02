package com.bacen;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonValue;
import jakarta.ws.rs.GET;

@TestInstance(Lifecycle.PER_CLASS)
public class SPIResourceTest {
    
    @BeforeAll
    public static void setup() {
        RestAssured.basePath = "/bacen/spi/request";
    }    

    @Test
    public void testCreateRequest() {
        
        final JsonArray json = Json.createArrayBuilder()
                .add(
                    Json.createObjectBuilder()
                        .add("bank", 0)
                        .add("branch", 0)
                        .add("account", 0)
                        .add("cpf", "22222222201")
                        .add("owner", "Bob")
                        .add("pkey", "22222222201")
                        .add("pkey_type", 0)
                        .add("end_to_end_id", JsonValue.NULL)
                        .add("creation_date", "2024-04-27T23:50:12.356+00:00")
                        .build())
                .add(
                    Json.createObjectBuilder()
                        .add("bank", 0)
                        .add("branch", 0)
                        .add("account", 0)
                        .add("cpf", "22222222201")
                        .add("owner", "Bob")
                        .add("pkey", "22222222201")
                        .add("pkey_type", 0)
                        .add("end_to_end_id", JsonValue.NULL)
                        .add("creation_date", "2024-04-27T23:50:12.356+00:00")
                        .build())
                .build();

        given()
            .contentType(ContentType.JSON)
            .body(json.toString())
            .post()
        .then()
            .statusCode(200);
    }

    @Test
    public void testConsultRequests() {

        given()
            .queryParam("resolved", false)
            .get()
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    public void testUpdateRequests() {
        
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
            .put()
        .then()
            .statusCode(200);
    }

    @GET
    public void testConsultUpdatedPixes() {

        given()
            .get("/state")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", greaterThan(0));
    }
}
