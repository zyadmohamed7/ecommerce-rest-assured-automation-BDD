package org.example.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItemPojo {
    private String itemId;
    private String name;
    private double price;
    private int quantity;

    public OrderItemPojo() { }

    public OrderItemPojo(String itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public OrderItemPojo(String itemId, String name, double price, int quantity) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    @JsonIgnore // Do not serialize to JSON
    public String getName() { return name; }
    @JsonProperty // Do deserialize from JSON
    public void setName(String name) { this.name = name; }

    @JsonIgnore // Do not serialize to JSON
    public double getPrice() { return price; }
    @JsonProperty // Do deserialize from JSON
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
