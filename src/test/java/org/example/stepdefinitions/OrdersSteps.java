package org.example.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.framework.apis.OrdersApi;
import org.example.framework.auth.AuthManager;
import org.example.context.TestContext;
import org.example.datagenerators.OrderDataGenerator;
import org.example.pojos.CreateOrderPojo;
import org.example.pojos.CustomerId;
import org.example.pojos.OrderItemPojo;
import org.example.testutils.SharedTestData;

import java.util.List;


import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
public class OrdersSteps {
    private final TestContext context;

    public OrdersSteps(TestContext context) {
        this.context = context;
    }

    @When("the admin sends a POST request to create a new order")
    public void adminSendsAPOSTRequestToCreateAnOrder() {
        AuthManager.setToken(SharedTestData.getAdminToken());
        CreateOrderPojo newOrder = OrderDataGenerator.generateOrderWithQuantity(
                context.getCustomerUsername(),
                context.getItemId(),
                1 // Order exactly 1 item so we can predictably deplete stock by exactly 1
        );
        context.setResponse(OrdersApi.createOrder(newOrder));
    }

    @When("the admin maliciously sends an order for 1000 copies of the item")
    public void adminMaliciouslySendsAnOrderFor1000Copies() {
        AuthManager.setToken(SharedTestData.getAdminToken());
        CreateOrderPojo newOrder = OrderDataGenerator.generateOrderWithQuantity(
                context.getCustomerUsername(),
                context.getItemId(),
                1000 // Malicious quantity
        );
        context.setResponse(OrdersApi.createOrder(newOrder));
    }

    @When("the admin sends a GET request to retrieve all orders")
    public void adminSendsAGetRequestToRetrieveAllOrders() {
        AuthManager.setToken(SharedTestData.getAdminToken());
        context.setResponse(OrdersApi.getOrders());
    }

    @When("the customer sends a GET request to checkout the order")
    public void customerSendsAGetRequestToCheckoutTheOrder() {
        AuthManager.setToken(SharedTestData.getCustomerToken());
        context.setResponse(OrdersApi.checkout(context.getOrderId()));
    }

    @When("the customer attempts to checkout the created order")
    public void customerAttemptsToCheckoutTheCreatedOrder() {
        AuthManager.setToken(SharedTestData.getCustomerToken());
        context.setResponse(OrdersApi.checkout(context.getOrderId()));
    }

    @When("the customer accidentally attempts to checkout the same order twice")
    public void customerAccidentallyAttemptsToCheckoutTwice() {
        AuthManager.setToken(SharedTestData.getCustomerToken());
        context.setResponse(OrdersApi.checkout(context.getOrderId()));
    }

    @When("the admin sends a GET request to retrieve all paid orders")
    public void adminSendsAGetRequestToRetrieveAllPaidOrders() {
        AuthManager.setToken(SharedTestData.getAdminToken());
        context.setResponse(OrdersApi.getPaidItems());
    }

    @When("the admin sends a DELETE request for the created order")
    public void adminSendsADeleteRequestForTheCreatedOrder() {
        AuthManager.setToken(SharedTestData.getAdminToken());
        context.setResponse(OrdersApi.deleteOrder(context.getOrderId()));
    }

    @When("the customer attempts to send a DELETE request for the created order")
    public void customerAttemptsToSendADeleteRequestForTheCreatedOrder() {
        AuthManager.setToken(SharedTestData.getCustomerToken());
        context.setResponse(OrdersApi.deleteOrder(context.getOrderId()));
    }

    @When("the admin attempts to checkout the created order")
    public void adminAttemptsToCheckoutTheCreatedOrder() {
        AuthManager.setToken(SharedTestData.getAdminToken());
        context.setResponse(OrdersApi.checkout(context.getOrderId()));
    }

    @And("the created order ID is stored for later use")
    public void createdOrderIDIsStoredForLaterUse() {
        CustomerId response = context.getResponse().as(CustomerId.class);
        assertThat("Order ID should not be null", response.getId(), notNullValue());
        SharedTestData.setCreatedOrderId(response.getId());
        context.setOrderId(response.getId()); // Syncing with TestContext immediately!
        System.out.println("✅ Created order ID: " + response.getId());
    }

    @And("all returned orders should have paid status")
    public void allReturnedOrdersShouldHavePaidStatus() {
        List<Boolean> paidStatuses = context.getResponse().jsonPath().getList("paid");
        assertThat("Paid orders list should not be empty", paidStatuses.size(), greaterThan(0));
        paidStatuses.forEach(paid -> assertThat("Order should have paid = true", paid, equalTo(true)));
    }

    @When("the customer attempts to order {int} copies of the item")
    public void theCustomerAttemptsToOrderCopiesOfTheItem(int quantity) {
        AuthManager.setToken(SharedTestData.getCustomerToken());
        CreateOrderPojo newOrder = OrderDataGenerator.generateOrderWithQuantity(
                context.getCustomerUsername(),
                context.getItemId(),
                quantity
        );
        context.setResponse(OrdersApi.createOrder(newOrder));
    }

    @And("the admin creates an order for the customer with {int} copies")
    public void theAdminCreatesAnOrderForTheCustomer(int quantity) {
        AuthManager.setToken(SharedTestData.getAdminToken());
        CreateOrderPojo newOrder = OrderDataGenerator.generateOrderWithQuantity(
                context.getCustomerUsername(),
                context.getItemId(),
                quantity
        );
        context.setResponse(OrdersApi.createOrder(newOrder));
    }

    @Then("the order total should be correct based on item price and quantity")
    public void theOrderTotalShouldBeCorrect() {
        // 1. Get the order details from the response
        double actualTotal = context.getResponse().jsonPath().getDouble("total");
        List<OrderItemPojo> items = context.getResponse().jsonPath().getList("items", OrderItemPojo.class);

        // 2. We expect only one item in this scenario for simplicity
        OrderItemPojo item = items.get(0);
        int quantity = item.getQuantity();

        // 3. We check the context for the pre-saved item price
        double expectedPrice = context.getItemPrice();
        double expectedTotal = expectedPrice * quantity;

        assertThat("Order total calculation is incorrect!", actualTotal, closeTo(expectedTotal, 0.01));
        System.out.println("✅ Mathematical verification passed: " + expectedPrice + " * " + quantity + " = " + actualTotal);
    }
}
