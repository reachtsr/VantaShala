package com.vs.rest.api.user;

import com.vs.model.user.User;
import com.vs.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */
@Component
@Path("/cook")
@Slf4j
public class CookController extends UserController {


    @Autowired
    public CookController(@Qualifier("cookService") IUserService userService) {
        super(userService);
    }

    @PostConstruct
    public void init() {
       log.info("{} Created.", CookController.class.getName());
    }


    @GET
    @Path("/list")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response listAllUsers() {
        return super.listUsers();
    }

    @GET
    @Path("/count")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserCount() {
        return super.getUserCount();
    }


    @GET
    @Path("/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response searchUser(@PathParam("name") String name) {
        return super.searchUser(name);
    }

    @GET
    @Path("/kitchenName/{kitchenName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUser(@PathParam("kitchenName") String kitchenName) {
        return super.getUserByKitchenName(kitchenName);
    }

    @GET
    @Path("/uname/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserByUserName(@PathParam("userName") String userName) {
        return super.getUserByUserName(userName);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createUser(User user){
        return super.createUser(user);
    }

    @POST
    @Path("/upload/{userName}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadUserIds(@PathParam("userName") String userName, @Context RequestContext request){
        return super.uploadIds(userName, request);
    }


    @PUT
    @Path("/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void updateUser(@PathParam("userName") String userName, User user){
       super.updateUser(userName, user);
    }

    @DELETE
    @Path("/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void deleteUser(@PathParam("userName") String userName){
       super.deleteUser(userName);
    }




}
