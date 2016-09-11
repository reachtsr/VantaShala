package com.vs.rest.api.user.admin;

import com.vs.model.enums.UserTypeEnum;
import com.vs.rest.api.user.CustomerController;
import com.vs.rest.api.user.UserController;
import com.vs.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by GeetaKrishna on 9/11/2016.
 */
@Component
@Path("/secret")
@Slf4j
public class AdminController extends UserController {

    @Autowired
    public AdminController(@Qualifier IUserService userService) {
        super(userService);
    }

    @PostConstruct
    public void init() {
        log.info("{} Initiated.", this.getClass().getName());
    }


    @GET
    @Path("/uname/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserByUserName(@PathParam("userName") String userName) {
        return super.getUserByUserName(userName);
    }

    @GET
    @Path("/list/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response listCook(@PathParam("userName") String name) {
        return super.listUsers();
    }

    @GET
    @Path("/list/cooks")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response listCooks() {
        return super.listUsers();
    }

    @GET
    @Path("/list/customers")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response listCustomers() {
        return super.listUsers();
    }

    @GET
    @Path("/list/users")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response listAllUsres() {
        return super.listUsers();
    }

    @GET
    @Path("/count")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserCount() {
        return super.getUserCount();
    }

    @GET
    @Path("/kitchenName/{kitchenName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUser(@PathParam("kitchenName") String kitchenName) {
        return super.getUserByKitchenName(kitchenName);
    }

    @DELETE
        @Path("/{userName}")
        @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
        public void deleteUser(@PathParam("userName") String userName){
            super.deleteUser(userName);
    }

    @GET
    @Path("/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response searchUser(@PathParam("name") String name) {
        return super.searchUser(name, UserTypeEnum.CUSTOMER);
    }

}
