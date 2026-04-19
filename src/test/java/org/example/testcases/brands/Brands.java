package org.example.testcases.brands;

import org.example.apis.BrandsEnpoint;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class Brands {
    private BrandsEnpoint brandsEnpoint = new BrandsEnpoint();

    @Test
    public void getBrandsList(){
        brandsEnpoint.getBrands()
                .then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("brandsSchema.json"))
                .log().all();
    }

    @Test
    public void updateBrands(){
        brandsEnpoint.updateBrands()
                .then()
                .assertThat()
                .statusCode(200)
                .log().all();
    }
}
