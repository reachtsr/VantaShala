package com.vs.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GeetaKrishna on 9/27/2016.
 */

@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public abstract class BaseController {
    public static Response build200Response(Object entity) {
        return Response.status(200).entity(entity).build();
    }

    public static Response buildOKResponse(Object object) {
        return Response.ok().entity(object).build();
    }

    public static Response build201Response(Object object) {

        if(object instanceof  String) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", object);
            return Response.status(201).entity(map).build();
        }
        else{
            return Response.status(201).entity(object).build();
        }

    }

    public static Response build404Response() {
        return Response.status(404).build();
    }
    public static Response build204Response() {
        return Response.status(204).build();
    }
}
