package com.vs.service.order;

import com.vs.model.enums.ItemStatus;
import com.vs.model.enums.OrderStatus;
import com.vs.model.order.UserMenuItem;
import com.vs.model.order.Order;
import com.vs.repository.OrderRepository;
import com.vs.service.email.EmailService;
import com.vs.service.menu.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Service
@Slf4j
public class OrderService implements IOrderService {

    @Autowired
    OrderRepository repository;

    @Autowired
    MongoTemplate template;

    @Autowired
    private MenuService menuService;

    @Autowired
    EmailService emailService;

    @Override
    public Order createOrder(Order order) {

        //Group By
        Map<String, List<UserMenuItem>> menuToItems = order.getUserMenuItems().stream().collect(Collectors.groupingBy(UserMenuItem::getMenuId));

        menuToItems.forEach((menuId,v) -> {
            v.forEach(menuToItem -> {
                menuService.updateUserMenuItemStatus(menuId, menuToItem.getItemId(), ItemStatus.LOCKED);
            });
        });

        Order savedOrder = repository.insert(order);
        emailService.sendOrderCreateEmail(savedOrder);

        return savedOrder;
    }

    @Override
    public Order updateOrder(Order order) {

        Order eOrder = getOrderById(order.getId());

        Order nOrder = repository.save(order);

        emailService.sendOrderCreateEmail(nOrder);
       // emailService.sendMenuStatusUpdateEmail(nOrder.getUserMenuItems());

        return nOrder;

    }

    // ToDo Add email Services to all order updates.
    @Override
    public void cancelOrder(String orderId) {
        Order eOrder = getOrderById(orderId);
        eOrder.setOrderStatus(OrderStatus.CANCELLED);
        Order nOrder = repository.save(eOrder);

    }

    @Override
    public List<Order> getAllCustomerOrders(String userName) {
        return repository.findByOrderedBy(userName);
    }

    @Override
    public List<Order> retrieveOrdersPlacedForCooks(final String cookId) {
        List<Order> orders = repository.findByCookMenuItemIn(cookId);
        log.info("Before processing Orders: {}", orders);
        Map<Order, List<UserMenuItem>> ordersToProcess = new HashMap<>();
        orders.forEach(order -> {
            List<UserMenuItem> userMenuItems = order.getUserMenuItems();
            List<UserMenuItem> itemsToRemove = new ArrayList<>();
            userMenuItems.forEach(userMenuItem -> {
                if(!userMenuItem.getCookId().equals(cookId)){
                    itemsToRemove.add(userMenuItem);
                }
            });
            ordersToProcess.put(order, itemsToRemove);
        });

        ordersToProcess.forEach((k,v) -> {
           if(orders.contains(k)){
               int index = orders.indexOf(k);
               orders.get(index).getUserMenuItems().removeAll(v);
           }
        });
        log.info("After processing Orders: {}", orders);
        return orders;
    }


    @Override
    public Order getOrderById(String orderId) {
        return repository.findById(orderId);
    }

    public boolean exists(String orderId) {
        return repository.exists(orderId);
    }
}
