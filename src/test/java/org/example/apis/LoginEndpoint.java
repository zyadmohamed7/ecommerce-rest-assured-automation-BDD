package org.example.apis;

import io.restassured.response.Response;
import org.example.apis.config.RequestSpecFactory;
import org.example.pojos.UserCredentialsPojo;

import static io.restassured.RestAssured.given;

public class LoginEndpoint {
    private static final String LOGIN_ENDPOINT = "/login";
    public Response login(UserCredentialsPojo userCredentialsPojo) {

        return given()
                .spec(RequestSpecFactory.getBaseSpec())
                .body(userCredentialsPojo)
                .when()
                .post(LOGIN_ENDPOINT);
    }
}
