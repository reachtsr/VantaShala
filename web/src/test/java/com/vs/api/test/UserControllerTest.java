package com.vs.api.test;

import com.jayway.restassured.RestAssured;
import com.mongodb.DuplicateKeyException;
import com.vs.bootstrap.ApplicationBootstrap;
import com.vs.common.errorHandling.AppException;
import com.vs.model.enums.UserStatusEnum;
import com.vs.model.user.Cook;
import com.vs.model.user.Customer;
import com.vs.model.user.address.Address;
import com.vs.model.user.address.BusinessAddress;
import com.vs.model.user.address.PersonalAddress;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.MediaType;
import java.util.UUID;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by GeetaKrishna on 12/19/2015.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ApplicationBootstrap.class)
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest extends BaseControllerTest {

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

    private Customer createCutomer() {
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
    public void a1_createCook() throws Exception {

        Cook cook = createCook();
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).body(cook).when().log().all().post("cook/");
        //when().get("/rest/user/helloUser").then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void a2_createCreateCookWithSameKitchenName() throws Exception {
        Cook cook = createCook();
        cook.setUserName(UUID.randomUUID().toString());
        expect().statusCode(500).given().contentType(MediaType.APPLICATION_JSON).body(cook).when().log().all().post("cook/");
    }

    @Test
    public void a3_tryDuplicateCook() throws Exception {
        Cook cook = createCook();
        expect().statusCode(500).given().contentType(MediaType.APPLICATION_JSON).body(cook).when().log().all().post("cook/");
    }

    @Test
    public void a4_createCustomer() throws Exception {
        Customer user = createCutomer();
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).body(user).log().all().when().post("customer/");
    }

    @Test
    public void a5_tryDuplicateCustomer() throws Exception {

        Customer user = createCutomer();
        expect().statusCode(500).given().contentType(MediaType.APPLICATION_JSON).body(user).when().log().all().post("customer/");
    }

    @Test
    public void a6_findCookByKitcheName() throws Exception {
        given().pathParam("kitchenName", kitchen_id).get("/cook/kitchenName/{kitchenName}").then().assertThat().body("kitchenName", equalTo(kitchen_id)).log().all();
    }

    @Test
    public void a7_disableUserCook() throws Exception {
        expect().statusCode(204).given().pathParam("userName", cook_id).put("/admn/user/{userName}/disable").then().log().all();
    }

    @Test
    public void a8_disableUserVerifyCook() throws Exception {
        given().pathParam("userName", cook_id).get("/admn/user/{userName}").then().assertThat().body("status", equalTo(UserStatusEnum.INACTIVE.name())).log().all();
    }

    @Test
    public void a9_enableUserCook() throws Exception {
        expect().statusCode(204).given().pathParam("userName", cook_id).put("/admn/user/{userName}/enable").then().log().all();
    }

    @Test
    public void b1_enableUserVerifyCook() throws Exception {
        given().pathParam("userName", cook_id).get("/admn/user/{userName}").then().assertThat().body("status", equalTo(UserStatusEnum.ACTIVE.name())).log().all();
    }

    @Test
    public void b2_disableUserCustomer() throws Exception {
        expect().statusCode(204).given().pathParam("userName", customer_id).put("/admn/user/{userName}/disable").then().log().all();
    }

    @Test
    public void b3_disableUserVerifyCustomer() throws Exception {
        given().pathParam("userName", customer_id).get("/admn/user/{userName}").then().body("status", equalTo(UserStatusEnum.INACTIVE.name())).log().all();
    }

    @Test
    public void b4_enableUserCustomer() throws Exception {
        expect().statusCode(204).given().pathParam("userName", customer_id).put("/admn/user/{userName}/enable").then().log().all();
    }

    @Test
    public void b5_enableUserVerifyCustomer() throws Exception {
        given().pathParam("userName", customer_id).get("/admn/user/{userName}").then().body("status", equalTo(UserStatusEnum.ACTIVE.name())).log().all();
    }

}
