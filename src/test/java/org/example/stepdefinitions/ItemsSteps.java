package org.example.stepdefinitions;

import io.cucumber.java.en.*;
import org.example.framework.apis.ItemsApi;
import org.example.framework.auth.AuthManager;
import org.example.context.TestContext;
import org.example.datagenerators.ItemDataGenerator;
import org.example.pojos.CreateItemPojo;
import org.example.pojos.ItemIdPojo;
import org.example.testutils.SharedTestData;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.example.framework.assertions.ApiAssert;

public class ItemsSteps {

    private final TestContext context;

    public ItemsSteps(TestContext context) {
        this.context = context;
    }

    @When("the admin sends a POST request to create a new item with random data")
    public void theAdminSendsPostRequestToCreateItem() {
        AuthManager.setToken(SharedTestData.getAdminToken());
        CreateItemPojo newItem = ItemDataGenerator.generateRandomItem();
        context.setResponse(ItemsApi.createItem(newItem));
    }

    @When("the customer attempts to send a POST request to create an item")
    public void theCustomerAttemptsToSendPostRequestToCreateItem() {
        AuthManager.setToken(SharedTestData.getCustomerToken());
        CreateItemPojo newItem = ItemDataGenerator.generateRandomItem();
        context.setResponse(ItemsApi.createItem(newItem));
    }

    @When("the admin sends a GET request for the created item")
    public void theAdminSendsGetRequestForCreatedItem() {
        AuthManager.setToken(SharedTestData.getAdminToken());
        context.setResponse(ItemsApi.getItem(context.getItemId()));
    }

    @When("the admin sends a PUT request to update the created item with random data")
    public void theAdminSendsPutRequestToUpdateItem() {
        AuthManager.setToken(SharedTestData.getAdminToken());
        CreateItemPojo updatedItem = ItemDataGenerator.generateRandomItem();
        context.setUpdatedItemName(updatedItem.getName());
        context.setUpdatedItemPrice(updatedItem.getPrice());
        context.setUpdatedItemStock(updatedItem.getStock());
        context.setResponse(ItemsApi.updateItem(context.getItemId(), updatedItem));
    }


    @When("the admin sends a DELETE request for the created item")
    public void theAdminSendsDeleteRequestForCreatedItem() {
        AuthManager.setToken(SharedTestData.getAdminToken());
        context.setResponse(ItemsApi.deleteItem(context.getItemId()));
    }

    @When("the admin checks the initial item stock")
    public void theAdminChecksTheInitialItemStock() {
        AuthManager.setToken(SharedTestData.getAdminToken());
        CreateItemPojo item = org.example.framework.assertions.ApiAssert
                .assertThatResponse(ItemsApi.getItem(context.getItemId()))
                .isStatusCode(200)
                .extractAs(CreateItemPojo.class);
        context.setInitialItemStock(item.getStock());
    }

    @When("the admin checks the item stock again")
    public void theAdminChecksTheItemStockAgain() {
        AuthManager.setToken(SharedTestData.getAdminToken());
        CreateItemPojo item = org.example.framework.assertions.ApiAssert
                .assertThatResponse(ItemsApi.getItem(context.getItemId()))
                .isStatusCode(200)
                .extractAs(CreateItemPojo.class);
        context.setUpdatedItemStock(item.getStock());
    }

    @Then("the current stock should be exactly {int} less than the initial stock")
    public void theCurrentStockShouldBeExactlyLess(int amount) {
        int expectedStock = context.getInitialItemStock() - amount;
        org.hamcrest.MatcherAssert.assertThat("Stock depleted incorrectly", context.getUpdatedItemStock(), org.hamcrest.Matchers.equalTo(expectedStock));
    }

    @Then("the current stock should be exactly {int} more than the initial stock")
    public void theCurrentStockShouldBeExactlyMore(int amount) {
        int expectedStock = context.getInitialItemStock() + amount;
        org.hamcrest.MatcherAssert.assertThat("Stock not restored correctly", context.getUpdatedItemStock(), org.hamcrest.Matchers.equalTo(expectedStock));
    }

    @Then("the current stock should be completely restored")
    public void theCurrentStockShouldBeCompletelyRestored() {
        org.hamcrest.MatcherAssert.assertThat("Stock was not restored", context.getUpdatedItemStock(), org.hamcrest.Matchers.equalTo(context.getInitialItemStock()));
    }

    @Then("the updated item data is reflected in the response")
    public void theUpdatedItemDataIsReflectedInTheResponse() {
        CreateItemPojo returnedItem = ApiAssert.assertThatResponse(context.getResponse())
                                               .extractAs(CreateItemPojo.class);

        assertThat("Name should match what was sent", returnedItem.getName(), equalTo(context.getUpdatedItemName()));
        assertThat("Price should match what was sent", returnedItem.getPrice(), equalTo(context.getUpdatedItemPrice()));
        assertThat("Stock should match what was sent", returnedItem.getStock(), equalTo(context.getUpdatedItemStock()));
    }

    @Then("the created item ID is stored for later use")
    public void theCreatedItemIdIsStoredForLaterUse() {
        try {
            ItemIdPojo response = context.getResponse().as(ItemIdPojo.class);
            assertThat("Item ID should not be null", response.getId(), notNullValue());
            SharedTestData.setCreatedItemId(response.getId());
            context.setItemId(response.getId()); // Syncing with TestContext immediately!
            System.out.println("✅ Created item ID: " + response.getId());
        } catch (Exception e) {
            System.err.println("Failed to create item, using backup ID");
            SharedTestData.setCreatedItemId(SharedTestData.getBackupItemId());
            context.setItemId(SharedTestData.getBackupItemId());
        }
    }

    @And("the item price is stored for later use")
    public void theItemPriceIsStoredForLaterUse() {
        double price = context.getResponse().jsonPath().getDouble("price");
        context.setItemPrice(price);
        System.out.println("✅ Stored item price for validation: " + price);
    }

    @When("the admin maliciously attempts to create an item with a stock of {int}")
    public void theAdminMaliciouslyAttemptsToCreateAnItemWithAStockOf(int stock) {
        AuthManager.setToken(SharedTestData.getAdminToken());
        CreateItemPojo maliciousItem = ItemDataGenerator.generateCustomItem("Malicious Item", 100.0, stock);
        context.setResponse(ItemsApi.createItem(maliciousItem));
    }

    @When("the admin maliciously attempts to create an item with a price of {double}")
    public void theAdminMaliciouslyAttemptsToCreateAnItemWithAPriceOf(double price) {
        AuthManager.setToken(SharedTestData.getAdminToken());
        CreateItemPojo maliciousItem = ItemDataGenerator.generateCustomItem("Malicious Item", price, 10);
        context.setResponse(ItemsApi.createItem(maliciousItem));
    }

    @When("the admin maliciously attempts to create an item with a name of {int} characters")
    public void theAdminAttemptsToCreateItemWithNameOfLength(int length) {
        AuthManager.setToken(SharedTestData.getAdminToken());
        CreateItemPojo maliciousItem = ItemDataGenerator.generateItemWithCustomLengths(length, 20);
        context.setResponse(ItemsApi.createItem(maliciousItem));
    }

    @When("the admin maliciously attempts to create an item with a description of {int} characters")
    public void theAdminAttemptsToCreateItemWithDescriptionOfLength(int length) {
        AuthManager.setToken(SharedTestData.getAdminToken());
        CreateItemPojo maliciousItem = ItemDataGenerator.generateItemWithCustomLengths(10, length);
        context.setResponse(ItemsApi.createItem(maliciousItem));
    }
}
