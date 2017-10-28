package com.vs.api.order;

import com.jayway.restassured.RestAssured;
import com.vs.api.common.BaseControllerTest;
import com.vs.api.common.ConstantsGenerator;
import com.vs.api.common.MenuConstantGenerator;
import com.vs.bootstrap.ApplicationBootstrap;
import com.vs.model.order.CookMenuItem;
import com.vs.model.order.Order;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.expect;

/**
 * Created by gopi on 11/12/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ApplicationBootstrap.class)
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderControllerTest extends BaseControllerTest {

    private Order createOrder() throws Exception {
        Order order = new Order();

        order.setId(ConstantsGenerator.generateId(ConstantsGenerator.TYPE.ORDER));
        //order.setOrderedBy(ConstantsGenerator.retriveRandomIdFromGeneratedList(ConstantsGenerator.TYPE.CUSTOMER));

        List<CookMenuItem> items = new ArrayList<>();

        for(int i=0; i<4; ++i) {
            CookMenuItem item = new CookMenuItem();
            String menuId = MenuConstantGenerator.retriveMenuIdFromGeneratedList();
            item.setMenuId(new ObjectId(menuId));
            item.setItemId(new ObjectId(MenuConstantGenerator.getMenuItemId(menuId)));
            item.setCookId(new ObjectId(ConstantsGenerator.retriveRandomIdFromGeneratedList(ConstantsGenerator.TYPE.COOK)));
            items.add(item);
        }
        order.setCookMenuItems(items);
        return  order;

    }
    @Test
    public void a1_createOrder() throws Exception {
        log.info("Creating Order {}", RestAssured.basePath);
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", MenuConstantGenerator.getCook_username()).
                body(createOrder()).log().all().when().post("/order/{userName}");

    }
    @Test @Ignore
    public void a2_createOrderWithInvalidUser() throws Exception {
        log.info("Creating Order {}", RestAssured.basePath);
        // null is not allowed and its an invalid test.
        expect().statusCode(500).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", null).log().all().
                body(createOrder()).log().all().when().post("/order/{userName}");
    }
    @Test
    public void a3_createOrderWithInvalidInput() throws Exception {
        log.info("Creating Order {}", RestAssured.basePath);
        Order order = createOrder();
        order.setCookMenuItems(null);
        expect().statusCode(500).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", MenuConstantGenerator.getCook_username()).
                body(order).log().all().when().post("/order/{userName}");

    }
    @Test
    public void a4_createOrderWithInvalidInput() throws Exception {
        log.info("Creating Order {}", RestAssured.basePath);
        Order order = createOrder();
        order.getCookMenuItems().clear();
        expect().statusCode(500).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", MenuConstantGenerator.getCook_username()).
                body(order).log().all().when().post("/order/{userName}");

    }
    @Test
    public void a5_createOrderWithInvalidInput() throws Exception {
        log.info("Creating Order {}", RestAssured.basePath);
        Order order = createOrder();
        order.getCookMenuItems().get(0).setItemId(null);
        expect().statusCode(500).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", MenuConstantGenerator.getCook_username()).
                body(order).log().all().when().post("/order/{userName}");

    }
    @Test
    public void a6_createOrderWithInvalidInput() throws Exception {
        log.info("Creating Order {}", RestAssured.basePath);
        Order order = createOrder();
        order.getCookMenuItems().get(0).setMenuId(null);
        expect().statusCode(500).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", MenuConstantGenerator.getCook_username()).
                body(order).log().all().when().post("/order/{userName}");

    }
    @Test
    public void a7_createOrderWithInvalidInput() throws Exception {
        log.info("Creating Order {}", RestAssured.basePath);
        Order order = createOrder();
        order.getCookMenuItems().get(0).setCookId(null);
        expect().statusCode(500).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", MenuConstantGenerator.getCook_username()).
                body(order).log().all().when().post("/order/{userName}");

    }
    @Test
    public void a8_createOrderWithInvalidInput() throws Exception {
        log.info("Creating Order {}", RestAssured.basePath);
        Order order = createOrder();
        order.getCookMenuItems().get(0).setCookId(null);
        order.getCookMenuItems().get(0).setMenuId(null);
        expect().statusCode(500).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", MenuConstantGenerator.getCook_username()).
                body(order).log().all().when().post("/order/{userName}");

    }
    @Test
    public void a9_createOrderWithInvalidInput() throws Exception {
        log.info("Creating Order {}", RestAssured.basePath);
        Order order = createOrder();
        order.getCookMenuItems().get(0).setCookId(null);
        order.getCookMenuItems().get(0).setMenuId(null);
        order.getCookMenuItems().get(0).setItemId(null);
        expect().statusCode(500).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", MenuConstantGenerator.getCook_username()).
                body(order).log().all().when().post("/order/{userName}");

    }
}
