package com.vs.service.order;

import com.vs.model.enums.MenuStatus;
import com.vs.model.order.Order;
import com.vs.repository.OrderRepository;
import com.vs.service.email.EmailService;
import com.vs.service.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Service
public class OrderService implements IOrderService {

    @Autowired
    OrderRepository repository;

    @Autowired
    private MenuService menuService;

    @Autowired
    EmailService emailService;

    @Override
    public Order createOrder(Order order) {

        Arrays.stream(order.getMenuIds()).forEach(
                menuId -> {
                    menuService.updateUserMenuStatus(menuId, MenuStatus.LOCKED);
                }
        );
        Order savedOrder = repository.save(order);
        emailService.sendOrderCreateEmail(savedOrder);

        return savedOrder;
    }

    @Override
    public Order updateOrder(Order order) {

        Order eOrder = getOrderById(order.getId());

        Arrays.stream(eOrder.getMenuIds()).forEach(
                menuId -> {
                    menuService.updateUserMenuStatus(menuId, MenuStatus.ACTIVE);
                }
        );

        Order nOrder = repository.save(order);

        Arrays.stream(order.getMenuIds()).forEach(
            menuId -> {
                menuService.updateUserMenuStatus(menuId, MenuStatus.LOCKED);
            }
        );

        emailService.sendOrderCreateEmail(nOrder);
        emailService.sendMenuStatusUpdateEmail(nOrder.getOrdersTo());

        return nOrder;

    }

    // ToDo Add email Services to all order updates.
    @Override
    public void cancelOrder(String orderId) {
        repository.delete(orderId);
    }

    @Override
    public List<Order> getOrders(String userName) {
        return repository.findByOrderedBy(userName);
    }

    @Override
    public Order getOrderById(String orderId) {
        return repository.findById(orderId);
    }

    public boolean exists(String orderId) {
        return repository.exists(orderId);
    }
}
