package com.vs.rest.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by gopi on 10/30/16.
 */



@Path("/")
@Controller
@Slf4j
public class EchoController extends BaseController{

    @Path("/echo")
    @GET
    public Response echo(){
        return Response.ok("I am alive!").build();
    }

    @PostConstruct
    public void init(){
        log.info("** Echo Controller Loaded **");
    }

}
