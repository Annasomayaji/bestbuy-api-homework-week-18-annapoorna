package com.bestbuy.crudtest;

import com.bestbuy.model.StorePojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.PropertyReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class StoresCRUDTest extends TestBase {

    String stores = PropertyReader.getInstance().getProperty("resourceStore");

    static String name = "StoreA";//for POST request
    static String name2 = "StoreB";//for PATCH request
    static String type = "Hardware";//for POST request
    static String type2 = "Software";//for PATCH request
    static String address = "24, The avenue";
    static String address2 = "PB";
    static String city = "London";
    static String state = "England";
    static String zip = "34dc5";
    static Double lat = 23.00;

    static Double lng = 45.00;
    static String hours = "Mon: 10-9";
    static HashMap<String, Object> services = new HashMap<String, Object>();
    static int storeId;
    static StorePojo storePojo = new StorePojo();

//    @BeforeClass
//    public void getStores() {
////        storePojo = new StorePojo();
//        given()
//                .when()
//                .get(stores);
//
//    }

    @Test(priority = 1)
    public void createStore() {


        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);
        storePojo.setLat(lat);
        storePojo.setLng(lng);
        storePojo.setHours(hours);
        storePojo.setServices(services);

        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .when()
                .body(storePojo)
                .post(stores)
                //response.prettyPrint();
                .then().statusCode(201);
        response.log().all();
        storeId = response.extract().path("id");
        System.out.println("*********************************************************");
        System.out.println("Store created with store id: " + storeId);
        System.out.println("*********************************************************");
    }

    @Test(priority = 2)
    public void getStoreById() {

        Response response = given()
                .pathParam("id", storeId)
                .when()
                .get(stores + "/{id}");
        response.prettyPrint();
        response.then()
                .statusCode(200);
        System.out.println("*********************************************************");
        System.out.println("Store records fetched for store id: " + storeId);
        System.out.println("*********************************************************");
    }

    @Test(priority = 3)
    public void updateStore() {

        storePojo.setName(name2); //change value of store name for update
        storePojo.setType(type2); //change value of store type for update

        ValidatableResponse response = given()
                .pathParam("id", storeId)
                .contentType(ContentType.JSON)
                .when()
                .body(storePojo)
                .patch(stores + "/{id}") //PATCH request
                //response.prettyPrint();
                .then().statusCode(200);
        response.log().all();
        System.out.println("*********************************************************");
        System.out.println("Store updated for store id:" + storeId);
        System.out.println("*********************************************************");
    }

    @Test(priority = 4)
    public void deleteStore() {
        given()
                .pathParam("id", storeId)
                .when()
                .get(stores + "/{id}")
                .then().statusCode(200);
        System.out.println("*********************************************************");
        System.out.println("Store deleted");
        System.out.println("*********************************************************");

    }

    @Test(priority = 5)
    public void verifyDeleteForStoreById() {
        System.out.println("Store id" + storeId );
        Response response = given()
                .pathParam("id", storeId)
                .when()
                .get(stores + "/{id}");
        response.prettyPrint();
        response.then()
                .statusCode(404);
        System.out.println("*********************************************************");
        System.out.println("Store " + storeId + " does not exist anymore");
        System.out.println("*********************************************************");

    }


}
