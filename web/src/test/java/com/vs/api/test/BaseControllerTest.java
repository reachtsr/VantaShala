package com.vs.api.test;

import com.jayway.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by GeetaKrishna on 9/15/2016.
 */
@Slf4j
public abstract class BaseControllerTest {

    @Value("${local.server.port}")
    int port;

    protected static String cook_id = "COOK_" + UUID.randomUUID().toString();
    protected static String customer_id = "CUSTOMER_" + UUID.randomUUID().toString();
    protected static String kitchen_id = "KITCHEN_" + UUID.randomUUID().toString();

    protected static String cookEmail = UUID.randomUUID().toString() + "_cook@cook.com";
    protected static String customerEmail = UUID.randomUUID().toString() + "_customer@customer.com";

    @Before
    public void setup() {

        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/vs/rest/";
        log.info("Setting up Tests {} - {}", port, RestAssured.DEFAULT_PATH);
        given().log().all();
    }

}
