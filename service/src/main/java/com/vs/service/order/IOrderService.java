package com.vs.service.order;

import com.vs.model.menu.Menu;
import com.vs.model.order.Order;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
public interface IOrderService {

    public Order createOrder(Order menu);
    public Order updateOrder(Order menu);
    public void cancelOrder(String id);
    public List<Order> getAllCustomerOrders(String userName);
    public Order getOrderById(String id);
    public List<Order> retrieveOrdersPlacedForCooks(String cookId);
}
