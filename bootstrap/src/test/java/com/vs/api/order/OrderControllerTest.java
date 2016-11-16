package com.vs.api.order;

import com.jayway.restassured.RestAssured;
import com.vs.api.common.BaseControllerTest;
import com.vs.api.common.ConstantsGenerator;
import com.vs.api.common.MenuConstantGenerator;
import com.vs.bootstrap.ApplicationBootstrap;
import com.vs.model.order.Order;
import com.vs.model.order.UserMenuItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
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

    private Order createOrder(){
        Order order = new Order();

        order.setId(ConstantsGenerator.generateId(ConstantsGenerator.TYPE.ORDER));
        order.setOrderedBy(ConstantsGenerator.retriveRandomIdFromGeneratedList(ConstantsGenerator.TYPE.CUSTOMER));

        List<UserMenuItem> items = new ArrayList<>();

        for(int i=0; i<4; ++i) {
            UserMenuItem item = new UserMenuItem();
            String menuId = MenuConstantGenerator.retriveRandomIdFromGeneratedList(ConstantsGenerator.TYPE.MENU);
            item.setMenuId(MenuConstantGenerator.getMenuItemId(menuId));
            item.setCookId(ConstantsGenerator.retriveRandomIdFromGeneratedList(ConstantsGenerator.TYPE.COOK));
            items.add(item);
        }
        order.setUserMenuItems(items);
        return  order;

    }
    @Test
    public void a1_createMenu() throws Exception {
        log.info("Creating Menu {}", RestAssured.basePath);
        expect().statusCode(200).given().contentType(MediaType.APPLICATION_JSON).pathParam("userName", MenuConstantGenerator.getCook_username()).
                body(createOrder()).log().all().when().post("/menu/{userName}");

    }
}
