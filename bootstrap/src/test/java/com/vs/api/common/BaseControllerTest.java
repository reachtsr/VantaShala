package com.vs.api.common;

import com.jayway.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by GeetaKrishna on 9/15/2016.
 */
@Slf4j
public abstract class BaseControllerTest {

    @Value("${local.server.port}")
    int port;

    @Before
    public void setup() {

        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/vs/rest/";
        log.info("Setting up Tests {} - {}", port, RestAssured.DEFAULT_PATH);
        given().log().all();

        log.info("Cook Id: {} Customer Id: {} Kitchen Id: {} Cook Email: {}, Customer Email: {}", ConstantsGenerator.COOK_ID, ConstantsGenerator.CUSTOMER_ID, ConstantsGenerator.KITCHEN_ID, ConstantsGenerator.COOK_EMAIL, ConstantsGenerator.CUSTOMER_EMAIL);
    }

}
