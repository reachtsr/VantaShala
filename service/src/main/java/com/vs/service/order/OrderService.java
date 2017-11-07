package com.vs.service.order;

import com.google.common.base.Preconditions;
import com.vs.model.enums.OrderStatus;
import com.vs.model.order.CookMenuItem;
import com.vs.model.order.Order;
import com.vs.repository.MenuRepository;
import com.vs.repository.OrderRepository;
import com.vs.service.email.EmailService;
import com.vs.service.menu.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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
    MenuRepository menuRepository;

    @Autowired
    MongoTemplate template;

    @Autowired
    private MenuService menuService;

    @Autowired
    EmailService emailService;

    @Autowired
    OrderCalculations orderCalculations;

    @Override
    public Order createOrder(Order order) throws Exception {

        //Group By
        Map<ObjectId, List<CookMenuItem>> menuToItems = order.getCookMenuItems().stream().collect(Collectors.groupingBy(CookMenuItem::getMenuId));
       // List<CookMenuItem> cookMenuItems = order.getCookMenuItems();

        double computedTotalPrice = orderCalculations.computeTotalPrice(order, menuToItems);
        Preconditions.checkState(computedTotalPrice != order.getTotalPrice());

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
        List<Order> orders = repository.findByCookMenuItemsIn(cookId);
        log.info("Before processing Orders: {}", orders);
        Map<Order, List<CookMenuItem>> ordersToProcess = new HashMap<>();
        orders.forEach(order -> {
            List<CookMenuItem> cookMenuItems = order.getCookMenuItems();
            List<CookMenuItem> itemsToRemove = new ArrayList<>();
            cookMenuItems.forEach(cookMenuItem -> {
                if(!cookMenuItem.getCookUserName().equals(cookId)){
                    itemsToRemove.add(cookMenuItem);
                }
            });
            ordersToProcess.put(order, itemsToRemove);
        });

        ordersToProcess.forEach((k,v) -> {
           if(orders.contains(k)){
               int index = orders.indexOf(k);
               orders.get(index).getCookMenuItems().removeAll(v);
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
