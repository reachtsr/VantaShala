package com.vs.service.order;

import com.vs.model.enums.OrderStatus;
import com.vs.model.order.Order;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
public interface IOrderService {

    public Order createOrder(Order menu) throws Exception;

    public Order updateOrder(Order menu);

    public void cancelOrder(String id);

    public List<Order> getAllCustomerOrders(String userName);

    public Order getOrderById(String id);

    public List<Order> retrieveOrdersForCooks(String cookId,  final OrderStatus status);
    public List<Order> retrieveAllOrdersTypesForCooks(String cookId);

    public boolean exists(String orderId);

}
