package org.example.stepdefinitions;

import io.cucumber.java.en.*;
import org.example.context.TestContext;
import org.example.utils.SharedTestData;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CommonSteps {

    private final TestContext context;

    public CommonSteps(TestContext context) {
        this.context = context;
    }

    @Given("the admin token is available")
    public void theAdminTokenIsAvailable() {
        assertThat("Admin token is null — login must run first", SharedTestData.getAdminToken(), notNullValue());
    }

    @Given("the customer token is available")
    public void theCustomerTokenIsAvailable() {
        assertThat("Customer token is null — login must run first", SharedTestData.getCustomerToken(), notNullValue());
    }

    @Given("the created item ID is available")
    public void theCreatedItemIdIsAvailable() {
        String itemId = SharedTestData.getCreatedItemId();
        assertThat("Item ID is null — create item must run first", itemId, notNullValue());
        context.setItemId(itemId);
    }

    @Given("the created order ID is available")
    public void theCreatedOrderIdIsAvailable() {
        String orderId = SharedTestData.getCreatedOrderId();
        assertThat("Order ID is null — create order must run first", orderId, notNullValue());
        context.setOrderId(orderId);
    }

    @Given("the customer username is available")
    public void theCustomerUsernameIsAvailable() {
        String username = SharedTestData.getCustomerUsername();
        assertThat("Customer username is null — login must run first", username, notNullValue());
        context.setCustomerUsername(username);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        int actualStatusCode = context.getResponse().getStatusCode();
        assertThat(
                "Expected status code " + expectedStatusCode + " but got " + actualStatusCode, actualStatusCode,
                equalTo(expectedStatusCode)
        );
    }

    @Then("the response body matches the {string} schema")
    public void responseBodyMatchesSchema(String schemaName) {
        context.getResponse()
                .then()
                .body(matchesJsonSchemaInClasspath(schemaName + "Schema.json"));
    }

    @Then("the system waits for deletion to complete")
    public void theSystemWaitsForDeletionToComplete() throws InterruptedException {
        Thread.sleep(2000);
    }
}
