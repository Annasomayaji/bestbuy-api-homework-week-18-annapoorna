package com.bestbuy.testbase;

import com.bestbuy.utils.PropertyReader;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class TestBase {
    String baseURI= PropertyReader.getInstance().getProperty("baseUri");
    int port=Integer.parseInt(PropertyReader.getInstance().getProperty("port"));
    @BeforeClass
    public void inIt(){
        RestAssured.baseURI=baseURI;
        RestAssured.port=port;
        //RestAssured.basePath="/stores";
    }
}
