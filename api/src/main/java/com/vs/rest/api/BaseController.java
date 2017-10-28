package com.vs.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by GeetaKrishna on 9/27/2016.
 */

@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public abstract class BaseController {
    public static Response build200Response(Object entity) {
        return Response.status(200).entity(entity).build();
    }

    public static Response buildOKResponse(Object object) {
        return Response.ok().entity(object).build();
    }

    public static Response build201Response(Object entity) {
        return Response.status(201).entity(entity).build();
    }

    public static Response build404Response(Object entity) {
        return Response.status(404).build();
    }
    public static Response build204Response(Object entity) {
        return Response.status(204).build();
    }
}
