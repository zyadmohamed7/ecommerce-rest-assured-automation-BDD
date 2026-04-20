package org.example.framework.apis;

import io.restassured.response.Response;
import org.example.framework.client.ApiClient;
import org.example.framework.config.Routes;
import org.example.pojos.CreateOrderPojo;

public class OrdersApi {

    public static Response createOrder(CreateOrderPojo body) {
        return ApiClient.send("POST", Routes.ORDERS.getPath(), body);
    }

    public static Response getOrders() {
        return ApiClient.send("GET", Routes.ORDERS.getPath(), null);
    }

    public static Response checkout(String id) {
        return ApiClient.send("POST", Routes.ORDERS.getPath() + "/" + id + "/checkout", null);
    }

    public static Response getPaidItems() {
        return ApiClient.send("GET", Routes.ORDERS.getPath() + "/paid", null);
    }

    public static Response deleteOrder(String id) {
        return ApiClient.send("DELETE", Routes.ORDERS.getPath() + "/" + id, null);
    }
}