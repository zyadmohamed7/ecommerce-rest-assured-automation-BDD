package org.example.framework.apis;

import io.restassured.response.Response;
import org.example.framework.client.ApiClient;
import org.example.framework.config.Routes;
import org.example.pojos.CreateItemPojo;

public class ItemsApi {

    public static Response createItem(CreateItemPojo body) {
        return ApiClient.send("POST", Routes.ITEMS.getPath(), body);
    }

    public static Response getItem(String id) {
        return ApiClient.send("GET", Routes.ITEMS.getPath() + "/" + id, null);
    }

    public static Response updateItem(String id, CreateItemPojo body) {
        return ApiClient.send("PUT", Routes.ITEMS.getPath() + "/" + id, body);
    }

    public static Response deleteItem(String id) {
        return ApiClient.send("DELETE", Routes.ITEMS.getPath() + "/" + id, null);
    }
}