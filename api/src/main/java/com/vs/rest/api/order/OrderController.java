package com.vs.rest.api.order;

import com.vs.model.enums.MenuStatus;
import com.vs.model.order.Order;
import com.vs.rest.api.common.CommonController;
import com.vs.service.menu.MenuService;
import com.vs.service.order.OrderService;
import jersey.repackaged.com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */

@Component
@Path("/order/")
@Slf4j
public class OrderController {


    @Autowired
    private OrderService orderService;


    @GET
    @Path("/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getOrders(@PathParam("userName") String userName) {
        Preconditions.checkNotNull(userName);
        List<Order> orders = orderService.getOrders(userName);
        return CommonController.buildResponse(orders);
    }

    @POST
    @Path("/{userName}")
    public Response createOrder(@PathParam("userName") String userName,  Order order) {
        Preconditions.checkNotNull(order.getOrderedBy());
        order.setOrderedBy(userName);
        Order nOrder = orderService.createOrder(order);
        return CommonController.buildResponse(nOrder);
    }

    @PUT
    @Path("/{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateOrder(@PathParam("orderId") String orderId, Order order) {
        Preconditions.checkNotNull(order.getOrderedBy());
        Preconditions.checkNotNull(orderService.exists(orderId));
        order.setId(orderId);
        orderService.updateOrder(order);
        return CommonController.buildResponse("");
    }


    @DELETE
    @Path("/{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteOrder(@PathParam("orderId") String orderId) {
        Preconditions.checkNotNull(orderId);
        orderService.deleteOrder(orderId);
        return CommonController.buildResponse("Order Deleted");
    }

}
