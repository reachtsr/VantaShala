package com.vs.api.test;

import com.jayway.restassured.RestAssured;
import com.vs.bootstrap.ApplicationBootstrap;
import com.vs.model.enums.Role;
import com.vs.model.user.BusinessAddress;
import com.vs.model.user.PersonalAddress;
import com.vs.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.ws.rs.core.MediaType;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;

/**
 * Created by GeetaKrishna on 12/19/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationBootstrap.class)
@WebAppConfiguration
@Slf4j
@IntegrationTest("server.port:0")
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
    public void getCook() throws Exception{

        log.info("Running test {}", RestAssured.basePath);
        given().log().path();

        User user = new User();
        com.vs.model.user.Address personalAddress = new PersonalAddress();
        com.vs.model.user.Address businessAddress = new BusinessAddress();

        personalAddress.setAddress1("ADDRESS1");
        personalAddress.setAddress2("ADDRESS2");
        personalAddress.setCity("FRISCO");
        personalAddress.setState("TX");
        personalAddress.setZipcode("75034");

        BeanUtils.copyProperties(businessAddress, personalAddress);

        user.setPersonalAddress(personalAddress);
        user.setBusinessAddress(businessAddress);
        user.setRole(Role.COOK);
        user.setFirstName("Gopi");
        user.setLastName("Kancharla");
        user.setKitchenName("VantaShala");
        user.setMobile("23123332312");

        expect().statusCode(200).when().get("/rest/user/cook/VantaShala").equals(user);
        // when().get("/rest/user/helloUser").then().statusCode(HttpStatus.SC_OK);

    }

    @Test
    public void createCook() throws Exception{

        log.info("Running test {}", RestAssured.basePath);
        given().log().path();

        User user = new User();
        com.vs.model.user.Address personalAddress = new PersonalAddress();
        com.vs.model.user.Address businessAddress = new BusinessAddress();

        personalAddress.setAddress1("ADDRESS1");
        personalAddress.setAddress2("ADDRESS2");
        personalAddress.setCity("FRISCO");
        personalAddress.setState("TX");
        personalAddress.setZipcode("75034");

        BeanUtils.copyProperties(businessAddress, personalAddress);

        user.setPersonalAddress(personalAddress);
        user.setBusinessAddress(businessAddress);
        user.setRole(Role.COOK);
        user.setFirstName("Gopi");
        user.setLastName("Kancharla");
        user.setKitchenName("VantaShala");
        user.setMobile("23123332312");
        user.setUserName("haigopi");
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).body(user).when().post("/rest/user/cook/");
       // when().get("/rest/user/helloUser").then().statusCode(HttpStatus.SC_OK);

    }

}
