package com.vs.service.order;

import com.vs.model.enums.ItemStatus;
import com.vs.model.enums.OrderStatus;
import com.vs.model.order.MenuToItem;
import com.vs.model.order.Order;
import com.vs.repository.OrderRepository;
import com.vs.service.email.EmailService;
import com.vs.service.menu.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Service
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
        Map<String, List<MenuToItem>> menuToItems = order.getItemToMenus().stream().collect(Collectors.groupingBy(MenuToItem::getMenuId));

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
        emailService.sendMenuStatusUpdateEmail(nOrder.getOrdersTo());

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
