package com.vs.api.test;

import com.jayway.restassured.RestAssured;
import com.vs.bootstrap.ApplicationBootstrap;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

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
    public void sayHello() throws Exception{

        log.info("Running test {}", RestAssured.basePath);
        given().log().path();
        when().get("/rest/user/helloUser").then().statusCode(HttpStatus.SC_OK);

    }
}
