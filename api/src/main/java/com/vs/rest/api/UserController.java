package com.vs.rest.api;

import com.vs.model.user.User;
import com.vs.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by GeetaKrishna on 12/14/2015.
 */
@Component
@Path("/user")
@Slf4j
public class UserController {

    public UserController() {
        log.info("Creating UserController");
    }

    @Autowired
    IUserService userService;

    @GET
    @Path("/helloUser")
    public Response helloTest() {
        log.info(" == Running Hello Test ==");
        return Response.status(200).build();
    }

    @POST
    @Path("/createCook")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void createCook(User user){

        log.info("User Details: {}", user);
        userService.saveCook(user);
    }

}
