package org.example.framework.client;

import io.restassured.response.Response;
import org.example.framework.config.RequestSpecFactory;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiClient {

    public static Response send(String method, String endpoint, Object body) {
        return org.example.framework.utils.RetryHandler.handle(() -> {
            RequestSpecification request = given()
                    .spec(RequestSpecFactory.getRequestSpec());

            if (body != null) {
                request.body(body);
            }

            return request
                    .when()
                    .request(method, endpoint)
                    .then()
                    .spec(RequestSpecFactory.getResponseSpec())
                    .extract()
                    .response();
        }, 5, 3000);
    }
}