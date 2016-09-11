package com.vs.api.test;

import com.jayway.restassured.RestAssured;
import com.vs.bootstrap.ApplicationBootstrap;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by GeetaKrishna on 9/11/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ApplicationBootstrap.class)
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdminControllerTest {


    @Test
    public void test3_listCook() throws Exception {

        log.info("Listing Cooks {}", RestAssured.basePath);
        given().log().path();
//        given().expect().statusCode(200).
//                when().get("cook/"+cook_id).then().assertThat().body("userName", equalTo("[cook]"));
    }

    @Test
    public void test4_listCustomer() throws Exception {

        log.info("Listing Cooks {}", RestAssured.basePath);
        given().log().path();
//        given().expect().statusCode(200).
//                when().get("customer/"+customer_id).then().assertThat().body("userName", equalTo("[customer]"));
    }

}
