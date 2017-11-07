package com.vs.rest.api.order;

import com.vs.model.enums.OrderStatus;
import com.vs.model.order.Order;
import com.vs.rest.api.BaseController;
import com.vs.service.order.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jersey.repackaged.com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class OrderController extends BaseController {

    @Autowired
    private IOrderService orderService;

    @GET
    @ApiOperation(value = "Retrieve All Orders created by this user", nickname = "getOrders")
    public Response getOrders(@HeaderParam("userName") String userName) {
        Preconditions.checkNotNull(userName);
        List<Order> orders = orderService.getAllCustomerOrders(userName);
        return build200Response(orders);
    }

    @GET
    @Path("/cook")
    @ApiOperation(value = "Retrieve All Orders created for a cook", nickname = "getOrdersCreatedForCook")
    public Response getCookSpecificOrders(@HeaderParam("userName") String userName) {
        Preconditions.checkNotNull(userName);
        List<Order> orders = orderService.retrieveOrdersPlacedForCooks(userName);
        return build200Response(orders);
    }


    @POST
    @ApiOperation(value = "Create an Order", nickname = "createAnOrder")
    public Response createOrder(@NotNull @HeaderParam("userName") String userName, @NotNull Order order) throws Exception {
        order.setOrderedBy(userName);
        Preconditions.checkNotNull(order.getOrderedBy(), "Who is ordering?");
        Preconditions.checkNotNull(order.getCookMenuItems(), "No Items found");
        Preconditions.checkState(order.getCookMenuItems().size() != 0, "No Items found");

        order.getCookMenuItems().forEach(t -> {
            Preconditions.checkNotNull(t.getCookUserName(), "No Cook found to place an order");
            Preconditions.checkNotNull(t.getItemId(), "Missing item found to place an order");
            Preconditions.checkNotNull(t.getMenuId(), "Missing cook's menu found to place an order");
        });

        Order nOrder = orderService.createOrder(order);
        return build200Response(nOrder);
    }

    @PUT
    @Path("/{orderId}")
    @ApiOperation(value = "Update an Order before CUT_OFF_DATE", nickname = "updateOrder")
    public Response updateOrder(@NotNull @HeaderParam("userName") String userName, @PathParam("orderId") String orderId, Order order) {
        Preconditions.checkNotNull(order.getOrderedBy());
        Preconditions.checkNotNull(orderService.exists(orderId));
        order.setId(orderId);
        orderService.updateOrder(order);
        return build200Response("");
    }

    @PUT
    @Path("/{orderId}/status/{status}")
    @ApiOperation(value = "Update an Order status", nickname = "updateOrderStatus")
    public Response updateOrderStatus(@PathParam("orderId") String id, @PathParam("status") OrderStatus status, @NotNull @HeaderParam("userName") String userName) {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(status);
        // PLACED, RECIEVED, PROCESSING, READY, DELEIVERED, CANCELLED
        return build200Response("Order Updated: " + id);
    }

}
