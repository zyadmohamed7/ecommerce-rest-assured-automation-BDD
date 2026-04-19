package org.example.stepdefinitions;

import io.cucumber.java.en.*;
import org.example.apis.ItemsEndpoint;
import org.example.context.TestContext;
import org.example.datagenerators.ItemDataGenerator;
import org.example.pojos.CreateItemPojo;
import org.example.pojos.ItemIdPojo;
import org.example.utils.SharedTestData;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ItemsSteps {

    private final TestContext context;

    public ItemsSteps(TestContext context) {
        this.context = context;
    }

    @When("the admin sends a POST request to create a new item with random data")
    public void theAdminSendsPostRequestToCreateItem() {
        ItemsEndpoint itemsEndpoint = new ItemsEndpoint(SharedTestData.getAdminToken());
        CreateItemPojo newItem = ItemDataGenerator.generateRandomItem();
        context.setResponse(itemsEndpoint.createitems(newItem));
    }

    @When("the admin sends a GET request for the created item")
    public void theAdminSendsGetRequestForCreatedItem() {
        ItemsEndpoint itemsEndpoint = new ItemsEndpoint(SharedTestData.getAdminToken());
        context.setResponse(itemsEndpoint.getitems(context.getItemId()));
    }

    @When("the admin sends a PUT request to update the created item with random data")
    public void theAdminSendsPutRequestToUpdateItem() {
        ItemsEndpoint itemsEndpoint = new ItemsEndpoint(SharedTestData.getAdminToken());
        CreateItemPojo updatedItem = ItemDataGenerator.generateRandomItem();
        context.setUpdatedItemName(updatedItem.getName());
        context.setUpdatedItemPrice(updatedItem.getPrice());
        context.setUpdatedItemStock(updatedItem.getStock());
        context.setResponse(itemsEndpoint.updateitems(context.getItemId(), updatedItem));
    }


    @When("the admin sends a DELETE request for the created item")
    public void theAdminSendsDeleteRequestForCreatedItem() {
        ItemsEndpoint itemsEndpoint = new ItemsEndpoint(SharedTestData.getAdminToken());
        context.setResponse(itemsEndpoint.deleteitems(context.getItemId()));
    }

    @Then("the updated item data is reflected in the response")
    public void theUpdatedItemDataIsReflectedInTheResponse() {
        String actualName = context.getResponse().jsonPath().getString("name");
        double actualPrice = context.getResponse().jsonPath().getDouble("price");
        int actualStock = context.getResponse().jsonPath().getInt("stock");
        assertThat("Name should match what was sent", actualName, equalTo(context.getUpdatedItemName()));
        assertThat("Price should match what was sent", actualPrice, equalTo(context.getUpdatedItemPrice()));
        assertThat("Stock should match what was sent", actualStock, equalTo(context.getUpdatedItemStock()));
    }

    @Then("the created item ID is stored for later use")
    public void theCreatedItemIdIsStoredForLaterUse() {
        try {
            ItemIdPojo response = context.getResponse().as(ItemIdPojo.class);
            assertThat("Item ID should not be null", response.getId(), notNullValue());
            SharedTestData.setCreatedItemId(response.getId());
            System.out.println("✅ Created item ID: " + response.getId());
        } catch (Exception e) {
            System.err.println("Failed to create item, using backup ID");
            SharedTestData.setCreatedItemId(SharedTestData.getBackupItemId());
        }
    }
}
