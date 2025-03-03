package com.bestbuy.crudtest;

import com.bestbuy.constant.EndPoints;
import com.bestbuy.model.ProductPojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;

public class ProductsCRUDTest extends TestBase {
    static int productId;
    public String name = "Duracell";
    public String type = "HardGood";
    public double price = 9.99;
    public String upc = "2563124";
    public String description = "Compatible with select electronic devices";
    public String model = "MN2400B4Z";

    @Test
    public void createProduct(){

        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setModel(model);

        ValidatableResponse response = given().log().ifValidationFails()
                .header("Content-Type", "application/json")
                .when()
                .body(productPojo)
                .post(EndPoints.PRODUCT)
                .then().log().ifValidationFails().statusCode(201);

        productId = response.extract().path("id");
        System.out.println("product id is : " + productId);
    }

    @Test
    public void getAllProducts(){
        ValidatableResponse response = given().log().ifValidationFails()
                .pathParam("productId", productId)
                .when()
                .get(EndPoints.GET_PRODUCT_BY_ID)
                .then().log().ifValidationFails().statusCode(200);

        productId = response.extract().path("id");
        System.out.println("product id is : " + productId);
    }

    @Test
    public void updateProduct(){
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name + "UpdatedName" + TestUtils.getRandomValue());
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setModel(model);

        ValidatableResponse response = given().log().ifValidationFails()
                .header("Content-Type", "application/json")
                .pathParam("productId", productId)
                .when()
                .body(productPojo)
                .put(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then().log().ifValidationFails().statusCode(200);

    }

    @Test
    public void deleteProduct(){
        given().log().ifValidationFails()
                .pathParam("productId", productId)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then()
                .statusCode(200);
    }
}
