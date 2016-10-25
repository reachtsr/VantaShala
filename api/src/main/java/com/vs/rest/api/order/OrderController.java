package com.vs.rest.api.order;

import com.vs.model.enums.OrderStatus;
import com.vs.model.order.Order;
import com.vs.rest.api.BaseController;
import com.vs.service.order.OrderService;
import io.swagger.annotations.Api;
import jersey.repackaged.com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */

@Component
@Path("/order/")
@Slf4j
@Api(value = "/order/", description = "Operations about Orders")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @GET
    @Path("/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getOrders(@PathParam("userName") String userName) {
        Preconditions.checkNotNull(userName);
        List<Order> orders = orderService.getOrders(userName);
        return buildResponse(orders);
    }

    @POST
    @Path("/{userName}")
    public Response createOrder(@PathParam("userName") String userName, Order order) {
        Preconditions.checkNotNull(order.getOrderedBy());
        order.setOrderedBy(userName);
        Order nOrder = orderService.createOrder(order);
        return buildResponse(nOrder);
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
        return buildResponse("");
    }

    @POST
    @Path("status/{orderId}/{status}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateOrderStatus(@PathParam("orderId") String id, @PathParam("status") OrderStatus status) {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(status);

        return buildResponse("Menu Updated: " + id);
    }


    @DELETE
    @Path("/{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response cancelOrder(@PathParam("orderId") String orderId) {
        Preconditions.checkNotNull(orderId);
        orderService.cancelOrder(orderId);
        return buildResponse("Order Deleted");
    }

}
