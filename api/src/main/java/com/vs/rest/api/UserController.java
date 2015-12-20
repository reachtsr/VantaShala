package com.vs.rest.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by GeetaKrishna on 12/14/2015.
 */
@Component
@Path("/hello")
@Slf4j
public class UserController {

    public UserController() {
        log.info("Obj Created");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello() {
        log.info("====> HELLO");
        return Response.status(200).build();
    }

}
