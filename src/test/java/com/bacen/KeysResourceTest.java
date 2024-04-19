package com.bacen;

// import org.junit.jupiter.api.Test;

// import org.jboss.logging.Logger;
import io.quarkus.test.junit.QuarkusTest;
// import io.restassured.http.ContentType;
// import jakarta.inject.Inject;

// import static io.restassured.RestAssured.given;

// import org.hamcrest.Matchers;

@QuarkusTest
public class KeysResourceTest {

    // @Inject
    // private Logger logger;

    // @Test
    // public void testKeysCheck() {
    //     String s = given()
    //         .contentType(ContentType.JSON)
    //         .body(new String[] {
    //             "mail@mail.com", "mail2@mail.com",
    //             "+5561123456789", "+5561888888888",
    //             "99999999999", "11111111101"
    //         })
    //         .post("/api/keys")
    //     .then()
    //         .extract().asString();
        
    //     logger.info(s);
            // TODO: response body = [true, false, false, false, false, false] ????
    //     given()
    //         .contentType(ContentType.JSON)
    //         .body(new String[] {
    //             "mail@mail.com", "mail2@mail.com",
    //             "+5561999999999", "+5561888888888",
    //             "99999999999", "99999999999999"
    //         })
    //         .post("/api/keys")
    //     .then()
    //         .statusCode(200)
    //         .body(
    //             "entries",
    //             Matchers.equalTo(new boolean[]{true, false, true, false, false, true})
    //         );
    // }
}
