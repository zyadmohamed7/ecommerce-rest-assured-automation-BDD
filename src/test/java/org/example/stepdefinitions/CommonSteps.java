package org.example.stepdefinitions;

import io.cucumber.java.en.*;
import org.example.context.TestContext;
import org.example.testutils.SharedTestData;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import org.example.framework.assertions.ApiAssert;
import static org.hamcrest.Matchers.*;
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
        ApiAssert.assertThatResponse(context.getResponse())
                 .isStatusCode(expectedStatusCode);
    }

    @And("the response status code should be 204 or 404")
    public void theResponseStatusCodeShouldBe204Or404() {
        int code = context.getResponse().getStatusCode();
        assertThat("Expected status 204 or 404, but found " + code,
                code, anyOf(is(204), is(404)));
    }

    @Then("the response body matches the {string} schema")
    public void responseBodyMatchesSchema(String schemaName) {
        ApiAssert.assertThatResponse(context.getResponse())
                 .matchesSchema(schemaName);
    }

    @Then("the response error message should say {string}")
    public void theResponseErrorMessageShouldSay(String expectedMessage) {
        String responseBody = context.getResponse().getBody().asString();
        assertThat("Error message not found in response", responseBody, containsString(expectedMessage));
    }

    @And("the response body should not contain the created order ID")
    public void theResponseBodyShouldNotContainTheCreatedOrderId() {
        String responseBody = context.getResponse().getBody().asString();
        String createdOrderId = context.getOrderId();
        assertThat("Order ID was found in response but should be private",
                responseBody, not(containsString(createdOrderId)));
    }

    @And("the response body should contain {string}")
    public void theResponseBodyShouldContain(String expectedContent) {
        String responseBody = context.getResponse().getBody().asString();
        assertThat("Response body did not contain: " + expectedContent,
                responseBody, containsString(expectedContent));
    }
}
