package com.vs.api.menu;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.vs.api.common.BaseControllerTest;
import com.vs.api.common.MenuConstantGenerator;
import com.vs.bootstrap.ApplicationBootstrap;
import com.vs.common.filters.AppConstants;
import com.vs.model.enums.ItemStatusEnum;
import com.vs.model.enums.Measurement;
import com.vs.model.enums.OrderCutOffHours;
import com.vs.model.menu.Item;
import com.vs.model.menu.Menu;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Created by GeetaKrishna on 10/7/2016.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ApplicationBootstrap.class)
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MenuControllerTest extends BaseControllerTest {


    private Menu createMenu() throws Exception {
        Menu menu = new Menu();
        List<Item> itemList = new ArrayList<>();
        menu.setEndDate(getNextWeek(getToday()).getTime());
        menu.setCutOffHours(OrderCutOffHours.FORTY_EIGHT);
        String menuId = MenuConstantGenerator.createMenuId();
        menu.setId(new ObjectId(menuId));
        menu.setUserName(MenuConstantGenerator.getCook_username());
        menu.setName("Healthy Week");
        menu.getEndDate();

        for (int i = 0; i <= 4; ++i) {
            Item item = new Item();
            item.setId(new ObjectId(MenuConstantGenerator.generateMenuItemId(menuId)));
            item.setName(TestItemEnum.randomItemName().toString());
            item.setPrice(ThreadLocalRandom.current().nextDouble(4, 11));
            item.setQuantity(ThreadLocalRandom.current().nextInt(4, 11 + 1));
            item.setMeasurement(Measurement.randomMeasurment());
            item.setDescription("RANDOM DESCRIPTION in maximum of 4 lines.");
            item.setStatus(ItemStatusEnum.ORDER_IN_PLACE);
            item.setCreatedDate(Calendar.getInstance().getTime());
            itemList.add(item);
        }

        menu.setItems(itemList);

        return menu;
    }

    @Test
    public void a1_createMenu() throws Exception {
        log.info("Creating Menu {}", RestAssured.basePath);
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", MenuConstantGenerator.getCook_username()).
                body(createMenu()).log().all().when().post("/menu/{userName}");

    }

    @Test
    public void a2_retriveUserMenu() throws Exception {
        given().pathParam("userName", MenuConstantGenerator.getCook_username()).get("/menu/{userName}").then().contentType(ContentType.JSON).log().all().
                body("size()", greaterThanOrEqualTo(1)).log().all();
    }


    @Test
    public void a3_createDuplicateMenu() throws Exception {
        log.info("Creating Menu {}", RestAssured.basePath);
        Menu menu = createMenu();
        // Don't move this line.
        MenuConstantGenerator.deleteMenu_id(menu.getId().toHexString());
        menu.setId(new ObjectId(MenuConstantGenerator.retriveMenuIdFromGeneratedList()));
        log.info("Existing: {} - New: {}", MenuConstantGenerator.retriveMenuIdFromGeneratedList(), menu.getId());
        expect().statusCode(500).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", MenuConstantGenerator.getCook_username()).
                body(menu).log().all().when().post("/menu/{userName}");


    }

    @Test
    public void a4_retriveSpecificUserMenu() throws Exception {
        given().pathParam("userName", MenuConstantGenerator.getCook_username()).get("/menu/{userName}").then().contentType(ContentType.JSON).log().all().
                body("size()", greaterThanOrEqualTo(1)).log().all();
    }

    @Test
    public void a5_retriveMenuBasedOnUserIdAndMenuId() throws Exception {
        given().pathParam("userName", MenuConstantGenerator.getCook_username()).pathParam("id", MenuConstantGenerator.getMenu_id()).get("/menu/{userName}/{id}").then().contentType(ContentType.JSON).log().all().
                body("size()", equalTo(1)).log().all();
    }

    @Test
    public void a6_updateMenuItemStatusToLOCKED() throws Exception {
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).
                pathParam("itemId", MenuConstantGenerator.getMenuItemId(MenuConstantGenerator.getMenu_id())).
                pathParam("id", MenuConstantGenerator.getMenu_id()).pathParam("status", ItemStatusEnum.ORDER_LIMIT_REACHED).
                post("/menu/status/{id}/{itemId}/{status}").then().contentType(ContentType.JSON).log().all();
    }

    @Test
    public void a7_tryDeleteLockedMenu() throws Exception {
        expect().statusCode(500).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", MenuConstantGenerator.getCook_username()).pathParam("id", MenuConstantGenerator.getMenu_id()).delete("/menu/{userName}/{id}").then().log().all();
    }

    @Test
    public void a8_updateMenuItemStatusToACTIVE() throws Exception {
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).
                pathParam("id", MenuConstantGenerator.getMenu_id()).pathParam("status", ItemStatusEnum.ORDER_IN_PLACE).
                pathParam("itemId", MenuConstantGenerator.getMenuItemId(MenuConstantGenerator.getMenu_id())).
                post("/menu/status/{id}/{itemId}/{status}").then().contentType(ContentType.JSON).log().all();
    }

    @Test
    public void a9_deleteActiveMenu() throws Exception {
        String menuId = MenuConstantGenerator.retriveMenuIdFromGeneratedList();
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", MenuConstantGenerator.getCook_username()).pathParam("id", menuId).delete("/menu/{userName}/{id}").then().contentType(ContentType.JSON).log().all();
        MenuConstantGenerator.deleteMenu_id(menuId);
    }

    @Test
    public void b10_createMenu() throws Exception {


        log.info("Creating Menu {}", RestAssured.basePath);

        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", MenuConstantGenerator.getCook_username()).
                body(createMenu()).log().all().when().post("/menu/{userName}");

    }

    @Test
    public void b11_updateMenu() throws Exception {
        log.info("Creating Menu {}", RestAssured.basePath);
        List<Menu> list = new ArrayList<>();
        list = Arrays.asList(given()
                .contentType(ContentType.JSON)
                .when().pathParam("userName", MenuConstantGenerator.getCook_username()).pathParam("id", MenuConstantGenerator.getMenu_id()).get("/menu/{userName}/{id}")
                .then()
                .extract().body().as(Menu[].class));

        log.info("Menu {}", list);

        list.get(0).setName("UPDATED_NAME");
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", MenuConstantGenerator.getCook_username()).
                body(list.get(0)).log().all().when().put("/menu/{userName}");

        given().pathParam("userName", MenuConstantGenerator.getCook_username()).pathParam("id", MenuConstantGenerator.getMenu_id()).get("/menu/{userName}/{id}").then().
                body("name", hasItem("UPDATED_NAME"));


    }

    @Test
    public void b12_retriveMenuBasedOnUserIdAndMenuId() throws Exception {
        given().pathParam("userName", MenuConstantGenerator.getCook_username()).pathParam("id", MenuConstantGenerator.getMenu_id()).get("/menu/{userName}/{id}").then().contentType(ContentType.JSON).log().all().
                body("size()", equalTo(1)).log().all();
    }

    @Test
    public void b13_menuUpdateName() throws Exception {
        given().pathParam("userName", MenuConstantGenerator.getCook_username()).pathParam("id", MenuConstantGenerator.getMenu_id()).get("/menu/{userName}/{id}").then().contentType(ContentType.JSON).log().all().
                body("name", hasItem("UPDATED_NAME")).log().all();
    }

    @Test
    public void b14_createMenu() throws Exception {
        log.info("Creating Menu {}", RestAssured.basePath);
        Menu menu = createMenu();
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", MenuConstantGenerator.getCook_username()).
                body(menu).log().all().when().post("/menu/{userName}");

    }

    @Test
    public void b15_retriveUserMenu() throws Exception {
        given().pathParam("userName", MenuConstantGenerator.getCook_username()).get("/menu/{userName}").then().contentType(ContentType.JSON).log().all().
                body("size()", greaterThanOrEqualTo(2)).log().all();
    }

    @Test
    public void b16_uploadItemPic() throws Exception {

        String filePath = System.getProperty("user.dir") + "/bootstrap/src/test/resources/gopi.jpg";
        filePath = filePath.replace("\\", "/");
        String menu_id = MenuConstantGenerator.getMenu_id();
        String item_id = MenuConstantGenerator.getMenuItemId(menu_id);
        String cookUserName = MenuConstantGenerator.getCook_username();
        log.info("Cook UserName: {}", cookUserName);

        given().pathParam("userName", cookUserName).pathParam("id", menu_id).pathParam("itemId", item_id).
                multiPart(new File(filePath)).
                expect().
                statusCode(200).
                when().
                post("/menu/upload/itemPicture/{userName}/{id}/{itemId}");

        given().pathParam("userName", cookUserName).get("/menu/{userName}").then().contentType(ContentType.JSON).log().all().
                body(AppConstants.MENU_ITEM_PICTURE_LOCATION,  not("null")).log().all();

    }

    @Test
    public void b17_createMenu() throws Exception {
        log.info("Creating Menu {}", RestAssured.basePath);
        for(int i=0; i<=3; ++i) {
            expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", MenuConstantGenerator.retriveMenuIdFromGeneratedList()).
                    body(createMenu()).log().all().when().post("/menu/{userName}");
        }

    }

}
