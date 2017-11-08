package com.vs.service.order;

import com.google.common.base.Preconditions;
import com.vs.model.enums.ItemStatus;
import com.vs.model.menu.Item;
import com.vs.model.order.CookMenuItem;
import com.vs.model.order.Order;
import com.vs.service.menu.MenuService;
import com.vs.service.menu.item.ItemService;
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

    public double computeTotalPrice(Order order, Map<ObjectId, List<CookMenuItem>> menuToItems) throws Exception {
        List<Double> prices = new ArrayList<>();
        menuToItems.forEach((menuId, v) ->
                v.forEach(menuToItem -> {
                    try {
                        Preconditions.checkNotNull(menuId);
                        Preconditions.checkNotNull(menuToItem.getItemId());
                        itemService.updateUserMenuItemStatus(menuId, menuToItem.getItemId(), ItemStatus.ORDER_IN_PLACE);
                        Item item = itemService.getMenuItem(menuId, menuToItem.getItemId());
                        Preconditions.checkNotNull(item);
                        prices.add(item.getPrice() * menuToItem.getOrderQuantity());
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
