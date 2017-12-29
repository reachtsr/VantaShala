package com.vs.rest.api.user;

import com.vs.model.enums.Role;
import com.vs.model.user.Cook;
import com.vs.model.user.Customer;
import com.vs.model.user.User;
import com.vs.service.user.IUserService;
import io.swagger.annotations.*;
import io.swagger.jaxrs.PATCH;
import jersey.repackaged.com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */
@Component
@Path("/customer")
@Slf4j
@Api(value = "Customer Management", description = "Customer Controller")
public class CustomerController extends UserController {

    @Autowired
    public CustomerController(@Qualifier("customerService") IUserService userService) {
        super(userService);
    }

    @PostConstruct
    public void init() {
        log.info("{} Initiated.", this.getClass().getName());
    }

    @GET
    @ApiOperation(value = "Find Customer", nickname = "FindCustomer")
    public Response getCustomer(@HeaderParam("userName") String userName) {
        Preconditions.checkNotNull(userName, "User Not found:" + userName);
        return super.getUserByUserName(userName);
    }

    @ApiOperation(value = "Create Customer", nickname = "createCustomer")
    @POST
    public Response createUser(Customer user) throws Exception{
        user.setEmail(user.getUserName());
        return super.createUser(user);
    }

    @ApiOperation(value = "Update Customer", nickname = "updateCustomer")
    @PUT
    public Response updateUser(@NotNull @HeaderParam("userName") String userName, @NotNull Customer user){
        return super.updateUser(userName, user);
    }

    @GET
    @Path("/subscribe/{cookId}")
    @ApiOperation(value = "Subscribe to Cook", nickname = "subscribeCustomerToCook")
    public Response subscribeCustomerToCookAPI(@NotNull @HeaderParam("userName") String customerId,
                                            @PathParam("cookId") String cookId) {
        return super.subscribeCustomerToCook(cookId, customerId);
    }


    @GET
    @Path("/subscriptions")
    @ApiOperation(value = "get Subscription for Customer", nickname = "getsubscriptionsforcustomer")
    public Response getSubscriptions(@HeaderParam("userName") String userName) {
        return super.getSubscriptions(userName, Role.CUSTOMER);
    }
}
