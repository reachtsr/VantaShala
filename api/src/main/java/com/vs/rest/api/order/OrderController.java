package com.vs.rest.api.order;

import com.vs.model.enums.OrderStatus;
import com.vs.model.order.Order;
import com.vs.rest.api.BaseController;
import com.vs.service.order.IOrderService;
import com.vs.service.order.OrderService;
import io.swagger.annotations.Api;
import jersey.repackaged.com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
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
    private IOrderService orderService;

    @GET
    @Path("/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getOrders(@PathParam("userName") String userName) {
        Preconditions.checkNotNull(userName);
        List<Order> orders = orderService.getAllCustomerOrders(userName);
        return build200Response(orders);
    }

    @GET
    @Path("/cook/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getCookSpecificOrders(@PathParam("userName") String userName) {
        Preconditions.checkNotNull(userName);
        List<Order> orders = orderService.retrieveOrdersPlacedForCooks(userName);
        return build200Response(orders);
    }


    @POST
    @Path("/{userName}")
    public Response createOrder(@NotNull @PathParam("userName") String userName, @NotNull Order order) throws Exception {
        order.setOrderedBy(userName);
        Preconditions.checkNotNull(order.getOrderedBy(), "Who is ordering?");
        Preconditions.checkNotNull(order.getCookMenuItems(), "No Items found");
        Preconditions.checkState(order.getCookMenuItems().size() != 0, "No Items found");

        order.getCookMenuItems().forEach(t -> {
            Preconditions.checkNotNull(t.getCookId(), "No Cook found to place an order");
            Preconditions.checkNotNull(t.getItemId(), "Missing item found to place an order");
            Preconditions.checkNotNull(t.getMenuId(), "Missing cook's menu found to place an order");
        });

        order.setOrderedBy(userName);
        Order nOrder = orderService.createOrder(order);
        return build200Response(nOrder);
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
        return build200Response("");
    }

    @POST
    @Path("status/{orderId}/{status}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateOrderStatus(@PathParam("orderId") String id, @PathParam("status") OrderStatus status) {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(status);

        return build200Response("Menu Updated: " + id);
    }


    @DELETE
    @Path("/{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response cancelOrder(@PathParam("orderId") String orderId) {
        Preconditions.checkNotNull(orderId);
        orderService.cancelOrder(orderId);
        return build200Response("Order Deleted");
    }

}
