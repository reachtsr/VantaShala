package com.vs.repository;

import com.vs.model.enums.OrderStatus;
import com.vs.model.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByOrderedBy(String name);

    Order findById(String id);

    List<Order> findByCookMenuItemsIn(String cookId);
    List<Order> findByCookMenuItems_CookUserNameAndOrderStatus(String cookId, OrderStatus status);
    List<Order> findByCookMenuItems_CookUserName(String cookId);
}
