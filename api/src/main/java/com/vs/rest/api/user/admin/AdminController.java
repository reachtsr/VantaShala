package com.vs.rest.api.user.admin;

import com.vs.model.enums.Role;
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
    public AdminController(@Qualifier("adminService") IUserService userService) {
        super(userService);
    }

    @PostConstruct
    public void init() {
        log.info("{} Initiated.", this.getClass().getName());
    }

    @GET
    @Path("/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserByUserName(@PathParam("userName") String userName) {
        return super.getUserByUserName(userName);
    }

    @GET
    @Path("/kitchenName/{kitchenName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getCook(@PathParam("kitchenName") String kitchenName) {
        return super.getCookByKitchenName(kitchenName);
    }

    @GET
    @Path("/list/cooks")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response listCooks() {
        return super.listUsers(Role.COOK);
    }

    @GET
    @Path("/list/customers")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response listCustomers() {
        return super.listUsers(Role.CUSTOMER);
    }

    @GET
    @Path("/list/users")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response listAllUsres() {
        return super.listUsers();
    }

    @GET
    @Path("/find/cook/{searchString}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findCookBySearchString(@PathParam("searchString") String searchString) {
        return super.findUserBySearchString(searchString, Role.COOK);
    }

    @GET
    @Path("/find/customer/{searchString}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findCustomerBySearchString(@PathParam("searchString") String searchString) {
        return super.findUserBySearchString(searchString, Role.CUSTOMER);
    }


    @GET
    @Path("/find/{searchString}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response findUserBySearchString(@PathParam("searchString") String searchString) {
        return super.findUserBySearchString(searchString);
    }

    @GET
    @Path("cook/count")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getCooksCount() {
        return super.getUserCount(Role.COOK);
    }

    @GET
    @Path("customer/count")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getCustomersCount() {
        return super.getUserCount(Role.CUSTOMER);
    }


    @GET
    @Path("/count")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllUserCount() {
        return super.getAllUserCount();
    }

    @DELETE
    @Path("/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void deleteUser(@PathParam("userName") String userName) {
        super.disableUser(userName);
    }

}
