package com.bestbuy.testsuite;

import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.PropertyReader;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class StoresExtractionTest extends TestBase {
    static ValidatableResponse response;
    String stores = PropertyReader.getInstance().getProperty("resourceStore");


    @BeforeClass
    public void getStores() {
        response = given()
                .when()
                .get(stores)
                .then()
                .statusCode(200);

    }

    //1. Extract the limit
    @Test
    public void extractLimit() {
        int limit = response.extract().path("limit");
        System.out.println("The value of limit is : " + limit);
        System.out.println("************************************");
    }

    //2.Extract the total
    @Test
    public void extractTotal() {
        int total = response.extract().path("total");
        System.out.println("The value of total is : " + total);
        System.out.println("************************************");
    }

    //3.Extract the name of 5th store
    @Test
    public void extractNameOfStore() {
        String name = response.extract().path("data[4].name");
        System.out.println("The name of the store is : " + name);
        System.out.println("************************************");
    }

    //4. Extract the names of all the store
    @Test
    public void extractNameOfAllStores() {
        List<String> allStoreNames = response.extract().path("data.name");
        System.out.println("The name of all the store are : " + allStoreNames);
        System.out.println("***********************************************************");
    }

    //5.Extract the storeId of all the store
    @Test
    public void extractStoreId() {

        List<Integer> allStoreIds = response.extract().path("data.id");
        System.out.println("The ids of all store are : " + allStoreIds);
        System.out.println("***********************************************************");

    }

    //6.Print the size of the data list
    @Test
    public void lengthOfDataList() {
        List<Map<String, Object>> data = response.extract().path("data");
        System.out.println("The length of data list is: " + data.size());
        System.out.println("***********************************************************");

    }

    //7. Get all the value of the store where store name = St Cloud
    @Test
    public void getValuesOfStore() {
        List<Map<String, Object>> list = response.extract().path("data.findAll{it.name=='St Cloud'}");
        System.out.println(list.get(0).values());
        System.out.println("***********************************************************");

    }

    //8.Get the address of the store where store name = Rochester
    @Test
    public void getAddress() {
        List<String> address = response.extract().path("data.findAll{it.name=='Rochester'}.address");
        System.out.println("The address is: " + address);
        System.out.println("***********************************************************");
    }

    //9.Get all the services of 8th store
    @Test
    public void getAllServices() {
        List<Map<String, ?>> listOfServices = response.extract().path("data[7].services.name");
        System.out.println("Services of 8th store: " + listOfServices);
        System.out.println("***********************************************************");

    }


    //10.Get storeservices of the store where service name = Windows Store
    @Test
    public void getStoreServices() {
        //List<Map<String, ?>> list = response.extract().path("data.services[5].findAll{it.name=='Windows Store'}.storeservices");
        List<HashMap<String,?>> storeServices=response.extract().path("data.collectMany{store->store.services.findAll{it.name=='Windows Store'}.collect{it.storeservices}}");

        System.out.println("Storeservices :" + storeServices);
        System.out.println("***********************************************************");

    }

    //  11. Get all the storeId of all the store
    @Test
    public void getStoreId() {
        List<Integer> list = response.extract().path("data.services.storeservices.storeId");
        System.out.println("All Store Ids: " + list);
        System.out.println("***********************************************************");

    }


    //12. Get id of all the store
    @Test
    public void getAllStoreId() {
        List<Integer> list = response.extract().path("data.id");
        System.out.println("Id of all stores: " + list);
        System.out.println("***********************************************************");

    }

    //13.Find the store names Where state = ND
    @Test
    public void getStoreNames() {
        List<String> list = response.extract().path("data.findAll{it.state='ND'}.name");
        System.out.println("Store names for state ND: " + list);
        System.out.println("***********************************************************");

    }

    //14.Find the Total number of services for the store where store name = Rochester
    @Test
    public void totalNumberOfServices() {
        List<Map<String, ?>> list = response.extract().path("data.findAll{name='Rochester'}.services");
        System.out.println("number od services in the store: " + list.size());
        System.out.println("***********************************************************");

    }

    // 15. Find the createdAt for all services whose name = “Windows Store”
    @Test
    public void createdAtForServices() {
        //System.out.println(Optional.ofNullable(response.extract().path("data.services")));
        List<String> list = response.extract().path("data.services*.findAll{it.name=='Windows Store'}.createdAt");
        System.out.println("Created at: " + list);
        System.out.println("***********************************************************");

    }

    //16. Find the name of all services Where store name = “Fargo”
    @Test
    public void nameOfServices(){
        List<String> list=response.extract().path("data.findAll{it.name=='Fargo'}.services.name");
        System.out.println("Services at store Fargo: "+ list);
        System.out.println("***********************************************************");

    }

    //17. Find the zip of all the store
    @Test
    public void zipOfStore(){
        List<String> list=response.extract().path("data.zip");
        System.out.println("Zip of all stores: "+list);
        System.out.println("***********************************************************");

    }

    //18. Find the zip of store name = Roseville
    @Test
    public void zipOfStoreName(){
        List<String> list=response.extract().path("data.findAll{it.name='Roseville'}.zip");
        System.out.println("Zip of all stores for RoseVille: "+list);
        System.out.println("***********************************************************");
    }

    //19. Find the store services details of the service name = Magnolia Home Theater
    @Test
    public void storeServices(){
        List<Map<String,?> > list=response.extract().path("data.services*.findAll{it.name=='Magnolia Home Theater'}.storeservices");
        System.out.println(list);
    }
    //20. Find the lat of all the stores
    @Test
    public void findLat(){
        List<Double> list=response.extract().path("data.lat");
        System.out.println("Lat of all stores for RoseVille: "+list);
        System.out.println("***********************************************************");
    }


}
