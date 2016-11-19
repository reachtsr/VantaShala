package com.vs.api.user;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.vs.api.common.BaseControllerTest;
import com.vs.api.common.ConstantsGenerator;
import com.vs.bootstrap.ApplicationBootstrap;
import com.vs.common.filters.AppConstants;
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
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.UUID;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

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
        cook.setKitchenName(ConstantsGenerator.getKitchen_name());
        cook.setMobile("23123332312");
        cook.setUserName(ConstantsGenerator.getCook_username());
        cook.setBusinessPhone("21321312321");
        cook.setEmail(ConstantsGenerator.getCook_email());

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
        user.setUserName(ConstantsGenerator.getCustomer_username());
        user.setEmail(ConstantsGenerator.getCustomer_email());

        return user;
    }

    @Test
    public void a0_a_healthCheck() {
        log.info("Echo Controller Test");
        expect().statusCode(200).when().log().all().get("/echo");
        com.jayway.restassured.response.Response response = get("/echo");
        log.info("{}", response.then().log().all());
        int status = response.statusCode();
        log.info("{}", response.toString());
        if (status != 200 && !response.body().print().equals("I am alive!")) {
            log.info("ECHO ITSELF FAILED.");
            System.exit(1);
        }
    }

    @Test
    public void a7_getUserByUserName() throws Exception {
        log.info("Listing Customers {}", RestAssured.basePath);
        given().pathParam("userName", ConstantsGenerator.getCook_username()).get("/admn/{userName}").then().assertThat().body("firstName", equalToIgnoringCase("Gopi")).log().all();
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
        cook.setUserName(ConstantsGenerator.generateId(ConstantsGenerator.TYPE.COOK));
        cook.setKitchenName(ConstantsGenerator.getKitchen_name());
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
    public void a6_findCookByKitchenName() throws Exception {
        given().pathParam("kitchenName", ConstantsGenerator.getKitchen_name()).get("/cook/kitchenName/{kitchenName}").then().assertThat().body("kitchenName", equalTo(ConstantsGenerator.getKitchen_name())).log().all();
    }

    @Test
    public void a7_disableUserCook() throws Exception {
        expect().statusCode(204).given().pathParam("userName", ConstantsGenerator.getCook_username()).put("/admn/{userName}/disable").then().log().all();
    }

    @Test
    public void a8_disableUserVerifyCook() throws Exception {
        given().pathParam("userName", ConstantsGenerator.getCook_username()).get("/admn/{userName}").then().assertThat().body("status", equalTo(UserStatusEnum.INACTIVE.name())).log().all();
    }

    @Test
    public void a9_enableUserCook() throws Exception {
        expect().statusCode(204).given().pathParam("userName", ConstantsGenerator.getCook_username()).put("/admn/{userName}/enable").then().log().all();
    }

    @Test
    public void b1_enableUserVerifyCook() throws Exception {
        given().pathParam("userName", ConstantsGenerator.getCook_username()).get("/admn/{userName}").then().assertThat().body("status", equalTo(UserStatusEnum.ACTIVE.name())).log().all();
    }

    @Test
    public void b2_disableUserCustomer() throws Exception {
        expect().statusCode(204).given().pathParam("userName", ConstantsGenerator.getCustomer_username()).put("/admn/{userName}/disable").then().log().all();
    }

    @Test
    public void b3_disableUserVerifyCustomer() throws Exception {
        given().pathParam("userName", ConstantsGenerator.getCustomer_username()).get("/admn/{userName}").then().body("status", equalTo(UserStatusEnum.INACTIVE.name())).log().all();
    }

    @Test
    public void b4_enableUserCustomer() throws Exception {
        expect().statusCode(204).given().pathParam("userName", ConstantsGenerator.getCustomer_username()).put("/admn/{userName}/enable").then().log().all();
    }

    @Test
    public void b5_enableUserVerifyCustomer() throws Exception {
        given().pathParam("userName", ConstantsGenerator.getCustomer_username()).get("/admn/{userName}").then().body("status", equalTo(UserStatusEnum.ACTIVE.name())).log().all();
    }

    @Test
    public void b6_uploadCookProfilePic() throws Exception {

        log.info("Execution path: {}", System.getProperty("user.dir"));
        String filePath = System.getProperty("user.dir") + "/bootstrap/src/test/resources/gopi.jpg";
        filePath = filePath.replace("\\", "/");
        String cookUserName = ConstantsGenerator.getCook_username();
        log.info("Cook UserName: {}", cookUserName);

        given().pathParam("userName", cookUserName).
                multiPart(new File(filePath)).
                expect().
                statusCode(200).
                when().
                post("/cook/upload/profile/{userName}");

        given().pathParam("userName", cookUserName).get("/admn/{userName}").then().log().all().assertThat().log().all().body(AppConstants.PROFILE_PICTURE, notNullValue()).log().all();
    }

    @Test
    public void b7_createMultipleCooks() throws Exception {

        for (int i = 0; i <= 10; ++i) {
            Cook cook = createCook();
            cook.setKitchenName(ConstantsGenerator.generateId(ConstantsGenerator.TYPE.KITCHEN));
            cook.setEmail(ConstantsGenerator.generateId(ConstantsGenerator.TYPE.COOK_EMAIL));
            cook.setUserName(ConstantsGenerator.generateId(ConstantsGenerator.TYPE.COOK));
            expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).body(cook).when().log().all().post("cook/");
        }
    }

    @Test
    public void b8_createMultipleCustomers() throws Exception {

        for (int i = 0; i <= 10; ++i) {
            Customer customer = createCutomer();
            customer.setEmail(ConstantsGenerator.generateId(ConstantsGenerator.TYPE.CUSTOMER_EMAIL));
            customer.setUserName(ConstantsGenerator.generateId(ConstantsGenerator.TYPE.CUSTOMER));
            expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).body(customer).log().all().when().post("customer/");
        }

    }

    @Test
    public void c1_subscribeCustomer() throws Exception {
        String cook = ConstantsGenerator.getCook_username();
        expect().statusCode(200).given().pathParam("cookId", cook).pathParam("customerId", ConstantsGenerator.getCustomer_username()).contentType(ContentType.JSON).
                post("customer/subscribe/{cookId}/{customerId}").then().log().all();
    }

    @Test
    public void c2_subscribeSameCustomer() throws Exception {
        String cook = ConstantsGenerator.getCook_username();
        expect().statusCode(200).given().pathParam("cookId", cook).pathParam("customerId", ConstantsGenerator.getCustomer_username()).contentType(ContentType.JSON).
                post("customer/subscribe/{cookId}/{customerId}").then().log().all();
    }

    @Test
    public void c3_subscribeNewCustomer() throws Exception {
        String cook = ConstantsGenerator.getCook_username();
        expect().statusCode(200).given().pathParam("cookId", cook).pathParam("customerId", ConstantsGenerator.retriveRandomIdFromGeneratedList(ConstantsGenerator.TYPE.CUSTOMER)).contentType(ContentType.JSON).
                post("customer/subscribe/{cookId}/{customerId}").then().log().all();
    }
}
