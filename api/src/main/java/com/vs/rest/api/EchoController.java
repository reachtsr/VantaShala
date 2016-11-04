package com.vs.rest.api;

import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by gopi on 10/30/16.
 */



@Path("/")
@Controller
public class EchoController extends BaseController{

    @Path("/echo")
    @GET
    public Response echo(){
        return Response.ok("I am alive!").build();
    }


}
