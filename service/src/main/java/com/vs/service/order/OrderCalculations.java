package com.vs.service.order;

import com.google.common.base.Preconditions;
import com.vs.model.enums.ItemStatus;
import com.vs.model.menu.Item;
import com.vs.model.order.CookMenuItem;
import com.vs.model.order.Order;
import com.vs.model.user.Cook;
import com.vs.model.user.User;
import com.vs.service.menu.MenuService;
import com.vs.service.menu.item.ItemService;
import com.vs.service.user.CookService;
import com.vs.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gopi on 11/19/2016.
 */
@Component
@Slf4j
public class OrderCalculations {

    @Autowired
    MenuService menuService;

    @Autowired
    ItemService itemService;

    @Autowired
    CookService cookService;

    public double attachItemAndcomputeTotalPrice(Order order, Map<ObjectId, List<CookMenuItem>> menuToItems) throws Exception {
        List<Double> prices = new ArrayList<>();
        menuToItems.forEach((menuId, v) ->
                v.forEach(menuToItem -> {
                    try {
                        Preconditions.checkNotNull(menuId);
                        Preconditions.checkNotNull(menuToItem.getItemId());
                        itemService.updateUserMenuItemStatus(menuId, menuToItem.getItemId(), ItemStatus.ORDER_IN_PLACE);
                        Item item = itemService.getMenuItem(menuId, menuToItem.getItemId());
                        Cook cook = (Cook)cookService.getUserByUserName(menuToItem.getCookUserName());
                        menuToItem.setItemDetails(item);
                        menuToItem.setCookDetails(cook);
                        Preconditions.checkNotNull(item);
                        prices.add(item.getPrice() * menuToItem.getOrderQuantity());

                        log.info("Order with item details: {}", order);
                    } catch (Exception e) {
                        log.error("", e);
                    }
                })
        );

        double totalPrice = prices.stream().mapToDouble(price -> price.doubleValue()).sum();
        log.info("Order: {} Total found: {}", order.getId(), totalPrice);
        return totalPrice;
    }
}
