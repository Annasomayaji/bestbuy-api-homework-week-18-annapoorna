package com.bestbuy.testsuite;

import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.PropertyReader;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ProductsExtractionTest extends TestBase {
    static ValidatableResponse response;
    String products = PropertyReader.getInstance().getProperty("resourceProducts");

    @BeforeClass
    public void getProducts() {
        response = given()
                .when()
                .get(products)
                .then()
                .statusCode(200);
    }

    //21. Extract the limit
    @Test
    public void extractLimit() {
        int limit = response.extract().path("limit");
        System.out.println("The value of limit is : " + limit);
        System.out.println("************************************");
    }

    //22. Extract the total
    @Test
    public void extractTotal() {
        int total = response.extract().path("total");
        System.out.println("The value of total is : " + total);
        System.out.println("************************************");
    }

    //23. Extract the name of 5th product
    @Test
    public void extractNameOfProduct() {
        String name = response.extract().path("data[4].name");
        System.out.println("The name of product is : " + name);
        System.out.println("************************************");
    }

    //24. Extract the names of all the products
    @Test
    public void nameOfAllProducts() {
        List<String> names = response.extract().path("data.name");
        System.out.println("The name of all the products are : " + names);
        System.out.println("************************************");

    }

    //25. Extract the productId of all the products
    @Test
    public void extractProductId() {
        List<Integer> productId = response.extract().path("data.id");
        System.out.println("The id of all the products are : " + productId);
        System.out.println("**************************************************");
    }

    //26. Print the size of the data list
    @Test
    public void printSizeOfDataList() {
        List<HashMap<String, ?>> data = response.extract().path("data");
        System.out.println("Size of data list is: " + data.size());
        System.out.println("**************************************************");

    }

    //27. Get all the value of the product where product name = Energizer - MAX Batteries AA (4-
    //Pack)
    @Test
    public void getValueOfProduct() {
        List<HashMap<String, ?>> allValues = response.extract().path("data.findAll{it.name=='Energizer - MAX Batteries AA (4-Pack)'}");
        // System.out.println(allValues.size());
        for (HashMap<String, ?> value : allValues) {
            System.out.println(value.values());
        }
        System.out.println("**************************************************");

    }

    //28. Get the model of the product where product name = Energizer - N Cell E90 Batteries (2-
    //Pack)
    @Test
    public void getModelOfProduct() {
        List<String> modelNames = response.extract().path("data.findAll{it.name=='Energizer - N Cell E90 Batteries (2-Pack)'}.model");
        System.out.println("The model of the product is: " + modelNames.get(0));
        System.out.println("**************************************************");

    }

    //29. Get all the categories of 8th products
    @Test
    public void getCategories() {
        List<HashMap<String, ?>> categories = response.extract().path("data[7].categories");
        System.out.println("The categories of the product is: " + categories);
        System.out.println("**************************************************");

    }

    //30. Get categories of the store where product id = 150115
    @Test
    public void getCategoriesForSpecificProductId() {
        List<HashMap<String, ?>> categories = response.extract().path("data.findAll{it.id==150115}.categories");
        System.out.println("The categories of the product is: " + categories);
        System.out.println("**************************************************");
    }

    //31. Get all the descriptions of all the products
    @Test
    public void getDescriptionOfProducts() {
        List<String> description = response.extract().path("data.description");
        System.out.println("The description of all the product are: " + description);
        System.out.println("**************************************************");
    }

    //32. Get id all categories of all the products
    @Test
    public void getIdOfAllCategoriesOfAllProducts() {
        List<String> categoryIds = response.extract().path("data.categories.id");
        System.out.println("The category ids of all the product are: " + categoryIds);
        System.out.println("**************************************************");
    }

    //33. Find the product names Where type = HardGood
    @Test
    public void getProductNameByType() {
        List<String> producNames = response.extract().path("data.findAll{it.type=='HardGood'}.name");
        System.out.println("The product names for HardDood are: " + producNames);
        System.out.println("**************************************************");

    }

    //34. Find the Total number of categories for the product where product name = Duracell - AA
    //1.5V CopperTop Batteries (4-Pack)
    @Test
    public void getTotalCategoriesByProductName() {
       List<String> allCategories = response.extract().path("data.findAll{it.name=='Duracell - AA 1.5V CopperTop Batteries (4-Pack)'}.categories");
        System.out.println("Number of categories for Duracell - AA 1.5V CopperTop Batteries (4-Pack) is: " + (allCategories.size()));
        System.out.println("**************************************************");

    }

    //35. Find the createdAt for all products whose price < 5.49
    @Test
    public void getCreatedAtDate() {
        List<String> createdDates = response.extract().path("data.findAll{it.price<5.49}.createdAt");
        System.out.println("Created date for price <5.49 is: " + createdDates);
        System.out.println("**************************************************");
    }

    //36. Find the name of all categories Where product name = “Energizer - MAX Batteries AA (4-Pack)”
    @Test
    public void getAllCategoriesByProductName() {
        List<String> allCategoryNames = response.extract().path("data.findAll{it.name=='Energizer - MAX Batteries AA (4-Pack)'}.categories.name");
        System.out.println("Names of categories for product name: " + allCategoryNames);
        System.out.println("**************************************************");
    }

    //37. Find the manufacturer of all the products
    @Test
    public void getManufacturerForProducts() {
        List<String> allManufacturerNames = response.extract().path("data.manufacturer");
        System.out.println("Names of Manufacturers : " + allManufacturerNames);
        System.out.println("**************************************************");
    }

    //38. Find the image of products whose manufacturer is = Energizer
    @Test
    public void getImageByManufacturer() {
        List<String> image = response.extract().path("data.findAll{it.manufacturer=='Energizer'}.image");
        System.out.println("images for Manufacturer : " + image);
        System.out.println("**************************************************");
    }

    //39. Find the createdAt for all categories products whose price > 5.99
    @Test
    public void getCreatedAtDateByPrice(){
        List<String> createdDate = response.extract().path("data.findAll{it.price>5.99}.createdAt");
        System.out.println("created date for price >5.99: " + createdDate);
        System.out.println("**************************************************");
    }

    //40. Find the url of all the products
    @Test
    public void getUrlOfAllProducts(){
        List<String> url=response.extract().path("data.url");
        System.out.println("Url for all products: " + url);
        System.out.println("**************************************************");
    }
}
