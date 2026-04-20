package org.example.framework.apis;

import io.restassured.response.Response;
import org.example.framework.client.ApiClient;
import org.example.framework.config.Routes;
import org.example.pojos.UserCredentialsPojo;

public class LoginApi {

    public static Response login(UserCredentialsPojo body) {
        return ApiClient.send("POST", Routes.LOGIN.getPath(), body);
    }
}