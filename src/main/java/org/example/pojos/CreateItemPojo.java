package org.example.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateItemPojo  {
    private String name;
    private String description;
    private double price;
    private int stock;

    public CreateItemPojo () {

    }

    public CreateItemPojo(String name) {
        this.name = name;
    }
    public CreateItemPojo(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public CreateItemPojo(double price) {
        this.price = price;
    }
    public CreateItemPojo(int stock) {
        this.stock = stock;
    }

    public CreateItemPojo (String name, String description, double price, int stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
}
