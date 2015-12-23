package com.vs.rest.api.common;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
public class CommonController {

    public static Response buildResponse(Object entity){
        return Response.status(200).entity(entity).build();
    }
}
