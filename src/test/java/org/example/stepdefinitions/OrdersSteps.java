package org.example.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en_scouse.An;
import org.example.apis.OrdersEndpoint;
import org.example.context.TestContext;
import org.example.datagenerators.OrderDataGenerator;
import org.example.pojos.CreateOrderPojo;
import org.example.pojos.CustomerId;
import org.example.utils.SharedTestData;

import java.util.List;


import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
public class OrdersSteps {
    private final TestContext context;

    public OrdersSteps(TestContext context) {
        this.context = context;
    }

    @When("the admin sends a POST request to create a new order")
    public void adminSendsAPostRequestToCreateANewOrder() {
        OrdersEndpoint ordersEndpoint = new OrdersEndpoint(SharedTestData.getAdminToken());
        CreateOrderPojo orderRequest = OrderDataGenerator.generateSingleItemOrder(context.getCustomerUsername(),
                context.getItemId());
        context.setResponse(ordersEndpoint.createOrder(orderRequest));
    }

    @When("the admin sends a GET request to retrieve all orders")
    public void adminSendsAGetRequestToRetrieveAllOrders() {
        OrdersEndpoint ordersEndpoint = new OrdersEndpoint(SharedTestData.getAdminToken());
        context.setResponse(ordersEndpoint.getOrder());
    }

    @When("the customer sends a GET request to checkout the order")
    public void customerSendsAGetRequestToCheckoutTheOrder() {
        OrdersEndpoint ordersEndpoint = new OrdersEndpoint(SharedTestData.getCustomerToken());
        context.setResponse(ordersEndpoint.getCheckout(context.getOrderId()));
    }

    @When("the admin sends a GET request to retrieve all paid orders")
    public void adminSendsAGetRequestToRetrieveAllPaidOrders() {
        OrdersEndpoint ordersEndpoint = new OrdersEndpoint(SharedTestData.getAdminToken());
        context.setResponse(ordersEndpoint.getPaidItems());
    }

    @When("the admin sends a DELETE request for the created order")
    public void adminSendsADeleteRequestForTheCreatedOrder() {
        OrdersEndpoint ordersEndpoint = new OrdersEndpoint(SharedTestData.getAdminToken());
        context.setResponse(ordersEndpoint.deleteorder(context.getOrderId()));
    }

    @And("the created order ID is stored for later use")
    public void createdOrderIDIsStoredForLaterUse() {
        CustomerId response = context.getResponse().as(CustomerId.class);
        assertThat("Order ID should not be null", response.getId(), notNullValue());
        SharedTestData.setCreatedOrderId(response.getId());
        System.out.println("✅ Created order ID: " + response.getId());
    }

    @And("all returned orders should have paid status")
    public void allReturnedOrdersShouldHavePaidStatus() {
        List<Boolean> paidStatuses = context.getResponse().jsonPath().getList("paid");
        assertThat("Paid orders list should not be empty", paidStatuses.size(), greaterThan(0));
        paidStatuses.forEach(paid -> assertThat("Order should have paid = true", paid, equalTo(true)));
    }
}
