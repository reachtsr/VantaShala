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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import javax.ws.rs.core.MediaType;
import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
/**
 * Created by GeetaKrishna on 12/19/2015.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class UserControllerTest {

    @Value("${local.server.port}")   // 6
    int port;

    @Before
    public void setup() {

        log.info("Setting up Tests {}", port);

        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/vs";
    }


    @Test
    public void listCook() throws Exception{

        log.info("Listing Cooks {}", RestAssured.basePath);
        given().log().path();
        given().expect().statusCode(200).
                when().get("/rest/cook/cook").then().assertThat().body("userName", equalTo("[cook]"));
    }

    @Test
    public void listCustomer() throws Exception{

        log.info("Listing Cooks {}", RestAssured.basePath);
        given().log().path();
        given().expect().statusCode(200).
                when().get("/rest/customer/customer").then().assertThat().body("userName", equalTo("[customer]"));
    }


    @Test
    public void createCook() throws Exception{

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
        cook.setKitchenName("VantaShala");
        cook.setMobile("23123332312");
        cook.setUserName("cook");
        cook.setBusinessPhone("21321312321");
        cook.setEmail("cook@cook.com");
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).body(cook).when().post("/rest/cook/");
        // when().get("/rest/user/helloUser").then().statusCode(HttpStatus.SC_OK);

    }

    @Test
    public void createCustomer() throws Exception{

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
        user.setUserName("customer");
        user.setEmail("customer@customer.com");
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).body(user).when().post("/rest/customer/");
       // when().get("/rest/user/helloUser").then().statusCode(HttpStatus.SC_OK);

    }

}
