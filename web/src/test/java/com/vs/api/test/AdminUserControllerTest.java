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
import static org.hamcrest.Matchers.*;

/**
 * Created by GeetaKrishna on 9/11/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ApplicationBootstrap.class)
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdminUserControllerTest extends BaseControllerTest {

    @Test
    public void a1_listCooks() throws Exception {
        log.info("Listing Cooks {}", RestAssured.basePath);
        given().get("/secret/list/cooks").then().body("firstName", hasItems("Gopi")).log().all();
    }

    @Test
    public void a2_listCustomers() throws Exception {

        log.info("Listing Customers {}", RestAssured.basePath);
        given().get("/secret/list/customers").then().body("firstName", hasItems("Gopi")).log().all();
    }

    @Test
    public void a3_listUsers() throws Exception {

        log.info("Listing all users {}", RestAssured.basePath);
        given().get("/secret/list/users").then().body("firstName", hasItems("Gopi")).log().all();
    }

    @Test
    public void a4_countCooks() throws Exception {
        log.info("Counting Customers {}", RestAssured.basePath);
        given().get("/secret/cook/count").then().body("count", greaterThanOrEqualTo(1)).log().all();
    }

    @Test
    public void a5_countCustomers() throws Exception {
        log.info("Counting Customers {}", RestAssured.basePath);
        given().get("/secret/cutomer/count").then().body("count", greaterThanOrEqualTo(1)).log().all();
    }

    @Test
    public void a6_countAllUsers() throws Exception {
        log.info("Counting Customers {}", RestAssured.basePath);
        given().get("/secret/count").then().body("count", greaterThanOrEqualTo(2)).log().all();
    }

    @Test
    public void a7_getUserByUserName() throws Exception {
        log.info("Listing Customers {}", RestAssured.basePath);
        given().pathParam("userName", cook_id).get("/secret/{userName}").then().body("firstName", hasItems("Gopi")).log().all();
    }

    @Test
    public void a8_findUserByFirstName() throws Exception {
        given().pathParam("searchString", "Gopi").get("/secret/find/{searchString}").then().body("searchString", equalTo("Gopi")).log().all();
    }

    @Test
    public void a9_findUserByLastName() throws Exception {
        given().pathParam("searchString", "Kancharla").get("/secret/find/{searchString}").then().body("searchString", equalTo("Kancharla")).log().all();
    }

    @Test
    public void b1_findCookByLastName() throws Exception {
        given().pathParam("searchString", "Kancharla").get("/secret/find/cook/{searchString}").then().body("searchString", equalTo("Kancharla")).log().all();
    }

    @Test
    public void b2_findCustomerByLastName() throws Exception {
        given().pathParam("searchString", "Kancharla").get("/secret/find/cook/{searchString}").then().body("searchString", equalTo("Kancharla")).log().all();
    }

    @Test
    public void b3_findCookByFirstName() throws Exception {
        given().pathParam("searchString", "Gopi").get("/secret/find/{searchString}").then().body("searchString", equalTo("Gopi")).log().all();
    }

    @Test
    public void b4_findCustomerByFirstName() throws Exception {
        given().pathParam("searchString", "Gopi").get("/secret/find/{searchString}").then().body("searchString", equalTo("Gopi")).log().all();
    }
}
