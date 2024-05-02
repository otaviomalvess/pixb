package com.bank;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@QuarkusTest
public class AccountResourceTest {
    
    @Test
    public void testCreateAccount() {

        final JsonObject json = Json.createObjectBuilder()
                .add("name", "Test")
                .add("cpf", "33333333301")
                .build();
        
        given()
            .contentType(ContentType.JSON)
            .body(json.toString())
            .post("/bank/account")
        .then()
            .statusCode(200);
    }

    @Test
    public void testGetAccountInfo() {

        given()
            .get("/bank/account/{cpf}", "33333333301")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("cpf", equalTo("33333333301"))
            .body("name", equalTo("Test"))
            .body("bank", equalTo(0))
            .body("branch", equalTo(0))
            .body("account", equalTo(0));
    }

    @Test
    public void testDeposit() {

        final JsonObject json = Json.createObjectBuilder()
                .add("value", 100.0)
                .build();
        
        given()
            .contentType(ContentType.JSON)
            .body(json.toString())
            .put("/bank/account/{cpf}/deposit", "33333333301")
        .then()
            .statusCode(200);
        
        given()
            .get("/bank/account/{cpf}", "33333333301")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("balance", equalTo(100.0f));
    }
}
