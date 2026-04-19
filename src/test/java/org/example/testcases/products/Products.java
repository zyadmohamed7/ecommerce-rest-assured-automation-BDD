package org.example.testcases.products;

import org.example.apis.ProductsEndpoint;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class Products {
    private ProductsEndpoint productsEndpoint = new ProductsEndpoint();


    @Test
    public void getAllTheProductsList() {
        productsEndpoint.getProducts()
                .then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("productsSchema.json"))
                .log().all();
    }

    @Test
    public void postProduct() {
        productsEndpoint.postProducts()
                .then()
                .assertThat()
                .statusCode(200)
                .log().all();
    }
}
