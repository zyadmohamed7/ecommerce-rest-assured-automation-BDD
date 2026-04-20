package org.example.framework.assertions;

import io.restassured.response.Response;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ApiAssert {

    private final Response response;

    private ApiAssert(Response response) {
        this.response = response;
    }

    public static ApiAssert assertThatResponse(Response response) {
        return new ApiAssert(response);
    }

    public ApiAssert isStatusCode(int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
        return this;
    }

    public ApiAssert matchesSchema(String schemaName) {
        response.then().body(matchesJsonSchemaInClasspath(schemaName + "Schema.json"));
        return this;
    }

    public <T> T extractAs(Class<T> clazz) {
        return response.as(clazz);
    }
}
