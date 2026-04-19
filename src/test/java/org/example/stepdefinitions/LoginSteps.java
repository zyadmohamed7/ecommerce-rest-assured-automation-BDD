package org.example.stepdefinitions;

import com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.java.en.*;
import org.example.apis.LoginEndpoint;
import org.example.context.TestContext;
import org.example.pojos.AdminToken;
import org.example.pojos.CustomerToken;
import org.example.pojos.UserCredentialsPojo;
import org.example.utils.JsonUtils;
import org.example.utils.SharedTestData;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginSteps {

    private final TestContext context;
    private final LoginEndpoint loginEndpoint;

    public LoginSteps(TestContext context) {
        this.context = context;
        this.loginEndpoint = new LoginEndpoint();
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
        System.out.println("Loaded credentials for: " + username);
    }



    @When("I send a POST request to the login endpoint")
    public void iSendPostRequestToLoginEndpoint() {
        context.setResponse(
                loginEndpoint.login(context.getCredentials())
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
        System.out.println("Customer token stored for " + username + ": " + token.getToken());
    }
}