package com.bestbuy.testsuite;

import com.bestbuy.model.StorePojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.PropertyReader;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class StoresAssertionTest extends TestBase {
    static String singleName = "Inver Grove Heights";
    static List<String> multipleNames = new ArrayList<String>(Arrays.asList("Roseville", "Burnsville", "Maplewood"));

    String stores = PropertyReader.getInstance().getProperty("resourceStore");
    static ValidatableResponse response; //Create response variable at class level to access them in all test methods
    StorePojo storePojo;
//
//    @BeforeClass
//    public void inIt() {
//        storePojo = new StorePojo();
//    }

    @BeforeClass
    public void getStores() {
        storePojo = new StorePojo();
        response = given()
                .when()
                .get(stores)
                .then()
                .statusCode(200);
//response.log().all();

    }

    //Verify if the total is equal to 1561
    @Test
    public void verifyStoreTotal() {
        response.body("total", equalTo(1561));
    }

    // Verify the if the stores of limit is equal to 10
    @Test
    public void verifyStoreLimit() {
        response.body("limit", equalTo(10));
    }

    //Check the single ‘Name’ in the Array list (Inver Grove Heights)
    @Test
    public void verifySingleName() {

      //  storePojo.setSingleName(singleName);
        response.body("data.name", hasItem("Inver Grove Heights"));
    }

    //Check the multiple ‘Names’ in the ArrayList (Roseville, Burnsville, Maplewood)
    @Test
    public void verifyMultipleNames() {
       // storePojo.setMultipleName(multipleNames);
       // List<String> list = storePojo.getMultipleName();
        response.body("data.name", hasItems("Roseville", "Burnsville", "Maplewood"));
    }

    //Verify the storied=7 inside store services of the third store of second services
    @Test
    public void verifyStoreId() {
        response.body("data[2].services[1].storeservices.storeId", equalTo(7));
    }

    //Check hash map values ‘createdAt’ inside storeservices map where store name = Roseville
    @Test
    public void verifyCreatedAt() {
        response.body("data.findAll{it.name=='Roseville'}.services.storeservices.get(0)", hasItem(hasValue("2016-11-17T17:57:09.417Z")));
    }

    //Verify the state = MN of forth store
   @Test
    public void verifyState(){
        response.body("data[3].state",equalTo("MN"));
   }

   //Verify the store name = Rochester of 9th store
    @Test
    public void verifyStoreName(){
        response.body("data[8].name",equalTo( "Rochester"));
    }

    //Verify the storeId = 11 for the 6th store
    @Test
    public void verifyStoreId2(){
        response.body("data[5].services.storeservices.storeId[0].",equalTo(11));
    }

    //Verify the serviceId = 4 for the 7th store of forth service
    @Test
    public void verifyServiceId(){
        response.body("data[6].services[3].storeservices.serviceId",equalTo(4));
    }
}
