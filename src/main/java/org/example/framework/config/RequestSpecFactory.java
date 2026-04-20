package org.example.framework.config;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.example.framework.auth.AuthManager;
import org.aeonbits.owner.ConfigFactory;

public class RequestSpecFactory {

    private static final RequestSpecification baseSpec;
    private static final ResponseSpecification baseResponseSpec;

    static {
        EnvironmentConfig config = ConfigFactory.create(EnvironmentConfig.class);

        baseSpec = new RequestSpecBuilder()
                .setBaseUri(config.baseUrl())
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .setRelaxedHTTPSValidation()
                .build();

        baseResponseSpec = new ResponseSpecBuilder()
                .build();
    }

    public static RequestSpecification getRequestSpec() {

        RequestSpecBuilder builder = new RequestSpecBuilder()
                .addRequestSpecification(baseSpec);

        String token = AuthManager.getToken();

        if (token != null) {
            builder.addHeader("Authorization", "Bearer " + token);
        }

        return builder.build();
    }

    public static ResponseSpecification getResponseSpec() {
        return baseResponseSpec;
    }
}