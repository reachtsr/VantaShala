package com.vs.rest.api.admin;

import com.vs.model.menu.Menu;
import com.vs.model.order.Order;
import com.vs.rest.api.BaseController;
import com.vs.service.order.IOrderService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by GeetaKrishna on 9/27/2016.
 */
@Component
@Path("/admn/order")
@Slf4j
@Api(value = "/admn/order", description = "Admin Order Controller")

public class AdminOrderController extends BaseController {

    IOrderService orderService;

    @GET
    @Path("/{userName}")
    public Response getMenusByUser(@PathParam("userName") String userName) {
        List<Order> orders = orderService.getAllCustomerOrders(userName);
        return build200Response(orders);
    }

}
