package com.vs.rest.api.user;

import com.vs.model.user.Customer;
import com.vs.model.user.User;
import com.vs.service.user.IUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */
@Component
@Path("/customer")
@Slf4j
@Api(value = "/customer", description = "Customer Controller")
public class CustomerController extends UserController {

    @Autowired
    public CustomerController(@Qualifier("customerService") IUserService userService) {
        super(userService);
    }

    @PostConstruct
    public void init() {
        log.info("{} Initiated.", this.getClass().getName());
    }


    @ApiOperation(value = "Create Customer", nickname = "createCustomer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Failure")})

    @POST
    @Path("/")
    public Response createUser(Customer user) throws Exception{
        return super.createUser(user);
    }

    @ApiOperation(value = "Update Customer", nickname = "updateCustomer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Failure")})

    @PUT
    @Path("/{userName}")
    public void updateUser(@PathParam("userName") String userName, Customer user){
        super.updateUser(userName, user);
    }

}
