package com.bestbuy.testsuite;

import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.PropertyReader;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProductsAssertionTest extends TestBase {
    static ValidatableResponse response;
    String products= PropertyReader.getInstance().getProperty("resourceProducts");

    @BeforeClass
    public void getProducts(){
        response=given()
                .when()
                .get(products)
                .then().statusCode(200);
    }

    //11. Verify the if the total is equal to 51957
    @Test
    public void verifyTotal(){
        response.body("total",equalTo(51957));
    }

    //12. Verify the if the stores of limit is equal to 10
    @Test
    public void verifyStoreLimit(){
        response.body("limit",equalTo(10));
    }

    //13. Check the single ‘Name’ in the Array list (Duracell - AAA Batteries (4-Pack))
    @Test
    public void verifySingleName(){
     response.body("data.name", hasItem("Duracell - AAA Batteries (4-Pack)"));
    }

    //14. Check the multiple ‘Names’ in the ArrayList (Duracell - AA 1.5V CopperTop Batteries ((4-
    //Pack), Duracell - AA Batteries (8-Pack), Energizer - MAX Batteries AA (4-Pack))

    @Test
    public void verifyMultipleNames(){
        response.body("data.name",hasItems("Duracell - AA 1.5V CopperTop Batteries (4-Pack)","Duracell - AA Batteries (8-Pack)","Energizer - MAX Batteries AA (4-Pack)"));

    }

    //15. Verify the productId=150115 inside categories of the third name is “Household Batteries”
    @Test
    public void verifyProductNameForProductId(){
        response.body("data.findAll{it.id==150115}.categories.name", contains("Household Batteries"));

        //data.collectMany{store->store.services.findAll{it.name=='Windows Store'}.collect{it.storeservices}}
    }

    //16. Verify the categories second name = “Housewares” of productID = 333179
    @Test
    public void verifyCategoryName(){
        response.body("data.findAll{it.id== 333179}.categories.get(0).name[1]", equalTo("Housewares"));
    }

    //17. Verify the price = 4.99 of forth product
    @Test
    public void verifyPrice(){
        response.body("data[3].price",equalTo(4.99));
    }

    //18. Verify the Product name = Duracell - D Batteries (4-Pack) of 6th product
    @Test
    public void verifyProductName(){
        response.body("data[5].name",equalTo("Duracell - D Batteries (4-Pack)"));
    }

    //19. Verify the ProductId = 333179 for the 9th product
    @Test
    public void verifyProductId(){
        response.body("data[8].id", equalTo(333179));
    }

    //20. Verify the prodctId = 346575 have 5 categories
   @Test
    public void verifyNumberOfCategories(){
        response.body("data.findAll{it.id==346575}.categories",hasItems(hasSize(5)));
   }

}
