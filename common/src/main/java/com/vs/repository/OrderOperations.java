package com.vs.repository;

import com.vs.model.enums.OrderStatus;
import com.vs.model.order.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Created by GeetaKrishna on 10-Nov-17.
 **/
@Component
@Slf4j
public class OrderOperations {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Order> listCookSpecificOrders(String cookUserName, OrderStatus status) {

        Query query = Query.query(where("orderStatus").is(status).and("cookMenuItems.cookUserName").is(cookUserName));
        List<Order> orders = mongoTemplate.find(query, Order.class);
        log.info("{}", orders);

        return orders;
    }
    public List<Order> listAllCookSpecificOrders(String cookUserName) {

        Query query = Query.query(where("cookMenuItems.cookUserName").is(cookUserName));
        List<Order> orders = mongoTemplate.find(query, Order.class);
        log.info("{}", orders);

        return orders;
    }

}
