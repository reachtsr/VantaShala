package com.vs.api.test;

import com.jayway.restassured.RestAssured;
import com.vs.bootstrap.ApplicationBootstrap;
import com.vs.model.user.Cook;
import com.vs.model.user.Customer;
import com.vs.model.user.address.Address;
import com.vs.model.user.address.BusinessAddress;
import com.vs.model.user.address.PersonalAddress;
import lombok.Data;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.ws.rs.core.MediaType;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Created by GeetaKrishna on 12/19/2015.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ApplicationBootstrap.class)
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {

    @Value("${local.server.port}")   // 6
            int port;

    private static String cook_id = "COOK_"+UUID.randomUUID().toString();
    private static String customer_id = "CUSTOMER_"+UUID.randomUUID().toString();
    private static String kitchen_id = "KITCHEN_"+UUID.randomUUID().toString();

    private static String cookEmail = UUID.randomUUID().toString()+"_cook@cook.com";
    private static String customerEmail = UUID.randomUUID().toString()+"_customer@customer.com";



    @Before
    public void setup() {

        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/vs/rest/";
        log.info("Setting up Tests {} - {}", port, RestAssured.DEFAULT_PATH);
        given().log().all();
    }

    private Cook createCook() throws Exception {

        log.info("Create Cook {}", RestAssured.basePath);
        given().log().path();

        Cook cook = new Cook();
        Address personalAddress = new PersonalAddress();
        Address businessAddress = new BusinessAddress();

        personalAddress.setAddress1("ADDRESS1");
        personalAddress.setAddress2("ADDRESS2");
        personalAddress.setCity("FRISCO");
        personalAddress.setState("TX");
        personalAddress.setZipCode("75034");

        BeanUtils.copyProperties(businessAddress, personalAddress);

        cook.setPersonalAddress(personalAddress);
        cook.setBusinessAddress(businessAddress);
        cook.setFirstName("Gopi");
        cook.setLastName("Kancharla");
        cook.setKitchenName(kitchen_id);
        cook.setMobile("23123332312");
        cook.setUserName(cook_id);
        cook.setBusinessPhone("21321312321");
        cook.setEmail(cookEmail);

        return cook;

    }

    private Customer createCutomer(){
        log.info("Running test {}", RestAssured.basePath);
        given().log().path();

        Customer user = new Customer();
        Address personalAddress = new PersonalAddress();

        personalAddress.setAddress1("ADDRESS1");
        personalAddress.setAddress2("ADDRESS2");
        personalAddress.setCity("FRISCO");
        personalAddress.setState("TX");
        personalAddress.setZipCode("75034");

        user.setPersonalAddress(personalAddress);
        user.setFirstName("Gopi");
        user.setLastName("Kancharla");
        user.setMobile("23123332312");
        user.setUserName(customer_id);
        user.setEmail(customerEmail);

        return user;
    }


    @Test
    public void test1_createCook() throws Exception {

        Cook cook = createCook();
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).body(cook).when().log().all().post("cook/");
        // when().get("/rest/user/helloUser").then().statusCode(HttpStatus.SC_OK);

    }


    @Test
    public void test2_createCreateCookWithSameKitchenName() throws Exception {

        Cook cook = createCook();
        cook.setUserName(UUID.randomUUID().toString());
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).body(cook).when().log().all().post("cook/");
        // when().get("/rest/user/helloUser").then().statusCode(HttpStatus.SC_OK);

    }


    @Test
    public void test3_tryDuplicateCook() throws Exception {

        Cook cook = createCook();
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).body(cook).when().log().all().post("cook/");
        // when().get("/rest/user/helloUser").then().statusCode(HttpStatus.SC_OK);

    }

    @Test
    public void test4_createCustomer() throws Exception {

        Customer user = createCutomer();
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).body(user).when().post("customer/");
        // when().get("/rest/user/helloUser").then().statusCode(HttpStatus.SC_OK);

    }
    @Test
    public void test5_tryDuplicateCustomer() throws Exception {

        Customer user = createCutomer();
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).body(user).when().post("customer/");
        // when().get("/rest/user/helloUser").then().statusCode(HttpStatus.SC_OK);

    }

}
