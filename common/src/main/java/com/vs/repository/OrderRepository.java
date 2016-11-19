package com.vs.repository;

import com.vs.model.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
public interface OrderRepository extends MongoRepository<Order, String> {

    public List<Order> findByOrderedBy(String name);

    public Order findById(String id);

    List<Order> findByCookMenuItemsIn(String cookId);

}
