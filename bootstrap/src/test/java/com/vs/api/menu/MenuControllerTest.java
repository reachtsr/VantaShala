package com.vs.api.menu;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.vs.api.common.BaseControllerTest;
import com.vs.api.common.ConstantsGenerator;
import com.vs.bootstrap.ApplicationBootstrap;
import com.vs.model.enums.Measurment;
import com.vs.model.enums.ItemStatus;
import com.vs.model.menu.Item;
import com.vs.model.menu.Menu;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;

/**
 * Created by GeetaKrishna on 10/7/2016.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ApplicationBootstrap.class)
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MenuControllerTest extends BaseControllerTest {

    String menu_id;
    String cook_id;

    @Before
    public void before() {
        menu_id = ConstantsGenerator.generateId(ConstantsGenerator.TYPE.MENU);
        cook_id = ConstantsGenerator.generateId(ConstantsGenerator.TYPE.MENU);
    }

    private Menu createMenu() {
        Menu menu = new Menu();
        List<Item> itemList = new ArrayList<>();
        menu.setEndDate(getNextWeek(getToday()).getTime());
        menu.setStartDate(getToday().getTime());
        menu.setMenuId(menu_id);
        menu.setUserName(cook_id.toString());
        menu.setName("Healthy Week");
        menu.getEndDate();

        for (int i = 0; i <= 4; ++i) {
            Item item = new Item();
            item.setId(ConstantsGenerator.generateMenuItemId(menu_id));
            item.setName(TestItemEnum.randomItemName().toString());
            item.setPrice(ThreadLocalRandom.current().nextDouble(4, 11));
            item.setQuatity(String.valueOf(ThreadLocalRandom.current().nextInt(4, 11 + 1)));
            item.setMeasurment(Measurment.randomMeasurment());
            item.setDescription("RANDOM DESCRIPTION in maximum of 4 lines.");
            item.setStatus(ItemStatus.ACTIVE);
            itemList.add(item);
        }

        menu.setItems(itemList);

        return menu;
    }

    @Test
    public void a1_createMenu() throws Exception {
        log.info("Creating Menu {}", RestAssured.basePath);
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", cook_id).
                body(createMenu()).log().all().when().post("/menu/{userName}");

    }

    @Test
    public void a2_retriveUserMenu() throws Exception {
        given().pathParam("userName", cook_id).get("/menu/{userName}").then().contentType(ContentType.JSON).log().all().
                body("size()", greaterThanOrEqualTo(1)).log().all();
    }


    @Test
    public void a3_createMenu() throws Exception {
        log.info("Creating Menu {}", RestAssured.basePath);
        expect().statusCode(500).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", cook_id).
                body(createMenu()).log().all().when().post("/menu/{userName}");

    }

    @Test
    public void a4_retriveUserMenu() throws Exception {
        given().pathParam("userName", cook_id).get("/menu/{userName}").then().contentType(ContentType.JSON).log().all().
                body("size()", greaterThanOrEqualTo(1)).log().all();
    }

    @Test
    public void a5_retriveMenuBasedOnUserIdAndMenuId() throws Exception {
        given().pathParam("userName", cook_id).pathParam("menuId", menu_id).get("/menu/{userName}/{menuId}").then().contentType(ContentType.JSON).log().all().
                body("size()", equalTo(1)).log().all();
    }

    @Test
    public void a6_updateMenuStatus() throws Exception {
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).pathParam("menuId", menu_id).pathParam("status", ItemStatus.LOCKED).post("/menu/status/{menuId}/{status}").then().contentType(ContentType.JSON).log().all();
    }

    @Test
    public void a7_deleteMenu() throws Exception {
        expect().statusCode(500).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", cook_id).pathParam("menuId", menu_id).delete("/menu/{userName}/{menuId}").then().contentType(ContentType.JSON).log().all();
    }

    @Test
    public void a8_updateMenuStatus() throws Exception {
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).pathParam("menuId", menu_id).pathParam("status", ItemStatus.ACTIVE).post("/menu/status/{menuId}/{status}").then().contentType(ContentType.JSON).log().all();
    }

    @Test
    public void a9_deleteMenu() throws Exception {
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", cook_id).pathParam("menuId", menu_id).delete("/menu/{userName}/{menuId}").then().contentType(ContentType.JSON).log().all();
    }

    @Test
    public void b10_createMenu() throws Exception {
        log.info("Creating Menu {}", RestAssured.basePath);
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", cook_id).
                body(createMenu()).log().all().when().post("/menu/{userName}");

    }

    @Test
    public void b11_updateMenu() throws Exception {
        log.info("Creating Menu {}", RestAssured.basePath);
        Menu menu = createMenu();
        menu.setName("UPDATED_NAME");
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", cook_id).
                body(menu).log().all().when().put("/menu/{userName}");

    }

    @Test
    public void b12_retriveMenuBasedOnUserIdAndMenuId() throws Exception {
        given().pathParam("userName", cook_id).pathParam("menuId", menu_id).get("/menu/{userName}/{menuId}").then().contentType(ContentType.JSON).log().all().
                body("size()", equalTo(1)).log().all();
    }

    @Test
    public void b13_menuUpdateName() throws Exception {
        given().pathParam("userName", cook_id).pathParam("menuId", menu_id).get("/menu/{userName}/{menuId}").then().contentType(ContentType.JSON).log().all().
                body("name", hasItem("UPDATED_NAME")).log().all();
    }

    @Test
    public void b14_createMenu() throws Exception {
        log.info("Creating Menu {}", RestAssured.basePath);
        Menu menu = createMenu();
        menu.setMenuId(ConstantsGenerator.generateId(ConstantsGenerator.TYPE.MENU));
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", cook_id).
                body(menu).log().all().when().post("/menu/{userName}");

    }

    @Test
    public void b15_retriveUserMenu() throws Exception {
        given().pathParam("userName", cook_id).get("/menu/{userName}").then().contentType(ContentType.JSON).log().all().
                body("size()", greaterThanOrEqualTo(2)).log().all();
    }
}
