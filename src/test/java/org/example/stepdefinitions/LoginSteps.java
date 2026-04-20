package org.example.stepdefinitions;

import com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.java.en.*;
import org.example.framework.apis.LoginApi;
import org.example.framework.auth.AuthManager;
import org.example.context.TestContext;
import org.example.pojos.AdminToken;
import org.example.pojos.CustomerToken;
import org.example.pojos.UserCredentialsPojo;
import org.example.utils.JsonUtils;
import org.example.testutils.SharedTestData;

import io.restassured.response.Response;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginSteps {

    private final TestContext context;

    public LoginSteps(TestContext context) {
        this.context = context;
    }


    @Given("I load credentials for {string} from users.json")
    public void iLoadCredentialsForUser(String username) throws IOException {
        List<UserCredentialsPojo> users = JsonUtils.readJsonFile(
                "users.json",
                new TypeReference<List<UserCredentialsPojo>>() {}
        );

        UserCredentialsPojo user = users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found in users.json: " + username));

        context.setCredentials(user);
        context.setCustomerUsername(username);
        System.out.println("Loaded credentials for: " + username);
    }



    @When("I send a POST request to the login endpoint")
    public void iSendPostRequestToLoginEndpoint() {
        context.setResponse(
                LoginApi.login(context.getCredentials())
        );
        System.out.println("POST /login sent for: " + context.getCredentials().getUsername());
    }


    @Then("the admin token is extracted and stored")
    public void theAdminTokenIsExtractedAndStored() {
        AdminToken token = context.getResponse()
                .then()
                .assertThat()
                .body("token", notNullValue())
                .body("token", not(isEmptyString()))
                .extract()
                .as(AdminToken.class);

        SharedTestData.setAdminToken(token.getToken());
        AuthManager.setToken(token.getToken());
        System.out.println("Admin token stored: " + token.getToken());
    }



    @Then("the customer token is extracted and stored for {string}")
    public void theCustomerTokenIsExtractedAndStoredFor(String username) {
        CustomerToken token = context.getResponse()
                .then()
                .assertThat()
                .body("token", notNullValue())
                .body("token", not(isEmptyString()))
                .extract()
                .as(CustomerToken.class);

        SharedTestData.setCustomerToken(token.getToken());
        SharedTestData.setCustomerUsername(username);
        AuthManager.setToken(token.getToken());
        System.out.println("Customer token stored for " + username + ": " + token.getToken());
    }

    @When("the user attempts to login with username {string} and password {string}")
    public void theUserAttemptsToLoginWith(String username, String password) {
        UserCredentialsPojo customCreds = new UserCredentialsPojo();
        customCreds.setUsername(username.equals("NULL") ? null : username);
        customCreds.setPassword(password.equals("NULL") ? null : password);
        context.setCredentials(customCreds);
        context.setResponse(LoginApi.login(customCreds));
    }

    @Then("the response should contain the error list {string}")
    public void theResponseShouldContainTheErrorList(String expectedError) {
        // Normalize both strings by removing backslashes and single quotes
        String normalizedExpected = expectedError.replace("\\", "").replace("'", "").trim();
        Response response = context.getResponse();

        // 1. Try 'error' field (singular)
        String errorField = response.path("error");
        if (errorField != null) {
            String normalizedActual = errorField.replace("\\", "");
            assertThat("Error message mismatch in 'error' field",
                    normalizedActual, containsString(normalizedExpected));
            return;
        }

        // 2. Try 'errors' field (list)
        try {
            String errorsList = response.path("errors").toString().replace("\\", "");
            assertThat("Error message mismatch in 'errors' list",
                    errorsList, containsString(normalizedExpected));
            return;
        } catch (Exception ignored) {}

        // 3. Fallback to normalized raw body
        String normalizedBody = response.getBody().asString().replace("\\", "");
        assertThat("Error message not found in normalized response body",
                normalizedBody, containsString(normalizedExpected));
    }
}