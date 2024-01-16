package com.bestbuy.crudtest;

import com.bestbuy.model.ProductPojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.PropertyReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ProductsCRUDTest extends TestBase {
    String products = PropertyReader.getInstance().getProperty("resourceProducts");
    static ProductPojo productPojo = new ProductPojo();
    static String name = "Duracell - AAA Batteries";
    static String name2 = "Alkaline Batteries";//For PATCH request
    static String type = "HardGood";
    static double price = 20.00;
    static int shipping = 0;
    static String upc = "1333424";
    static String description = "4-pack";
    static String manufacturer = "Duracell";
    static String model = "MN2400B4Z";
    static String url = "http://www.bestbuy.com/site/duracell-aaa-batteries-4-pack/43900.p?id=1051384074145&skuId=43900&cmp=RMXCC";
    static String img = "no image";

    static int productId; //to store product id created in POST request
    static ValidatableResponse response;

    @Test(priority = 1)
    public void createNewStore() {
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);
        productPojo.setShipping(shipping);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(img);

        response = given()
                .contentType(ContentType.JSON)
                .when()
                .body(productPojo)
                .post(products)
                .then();
        response.statusCode(201);
        response.log().all();

//Extract the product id created
        productId = response.extract().path("id");
        System.out.println("****************************************************");
        System.out.println("Product created with id: " + productId);
        System.out.println("****************************************************");
    }

    @Test(priority = 2)
    public void getProductById() {
        Response response = given()
                .pathParam("id", productId)
                .when()
                .get(products + "/{id}");
        response.prettyPrint();
        response.then()
                .statusCode(200);

        //response.log().all();
        System.out.println("****************************************************");
        System.out.println("Product fetched for product id: " + productId);
        System.out.println("****************************************************");

    }

    @Test(priority = 3)
    public void updateProduct(){
        productPojo.setName(name2);
       response= given()
                .pathParam("id",productId)
                .contentType(ContentType.JSON)
                .when()
                .body(productPojo)
                .patch(products+"/{id}")
                .then()
               .statusCode(200);
       response.log().all();
        System.out.println("****************************************************");
        System.out.println("Product updated for product id: " + productId);
        System.out.println("****************************************************");

    }

    @Test(priority = 4)
    public void deleteProduct(){
        response=given()
                .pathParam("id",productId)
                .when()
                .delete(products+"/{id}")
                .then()
                .statusCode(200);
        response.log().all();
        System.out.println("****************************************************");
        System.out.println("Product "+productId+ " deleted." );
        System.out.println("****************************************************");
    }

    @Test(priority = 5)
    public void verifyDeleteForProductById() {
        Response response = given()
                .pathParam("id", productId)
                .when()
                .get(products + "/{id}");
        response.prettyPrint();
        response.then()
                .statusCode(404);

        //response.log().all();
        System.out.println("****************************************************");
        System.out.println("Product " + productId + " does not exist anymore");
        System.out.println("****************************************************");

    }
}
