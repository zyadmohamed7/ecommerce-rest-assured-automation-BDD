package org.example.context;

import io.restassured.response.Response;
import org.example.pojos.UserCredentialsPojo;

public class TestContext {

    private UserCredentialsPojo credentials;
    private Response response;
    private String extractedToken;
    private String itemId;
    private String orderId;
    private String customerUsername;
    private String updatedItemName;
    private double updatedItemPrice;
    private int updatedItemStock;
    private int initialItemStock;
    private double itemPrice;

    public double getItemPrice() {
        return itemPrice;
    }
    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getInitialItemStock() {
        return initialItemStock;
    }
    public void setInitialItemStock(int initialItemStock) {
        this.initialItemStock = initialItemStock;
    }

    public String getUpdatedItemName() {
        return updatedItemName;
    }
    public void setUpdatedItemName(String name) {
        this.updatedItemName = name;
    }

    public double getUpdatedItemPrice() {
        return updatedItemPrice;
    }
    public void setUpdatedItemPrice(double price) {
        this.updatedItemPrice = price;
    }

    public int getUpdatedItemStock() {
        return updatedItemStock;
    }
    public void setUpdatedItemStock(int stock) {
        this.updatedItemStock = stock;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }



    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }



    public UserCredentialsPojo getCredentials() {
        return credentials;
    }
    public void setCredentials(UserCredentialsPojo credentials) {
        this.credentials = credentials;
    }

    public Response getResponse() {
        return response;
    }
    public void setResponse(Response response) {
        this.response = response;
    }

    public String getExtractedToken() {
        return extractedToken;
    }
    public void setExtractedToken(String token) {
        this.extractedToken = token;
    }
}