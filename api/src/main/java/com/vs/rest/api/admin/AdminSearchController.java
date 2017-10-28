package com.vs.rest.api.admin;

import com.vs.model.order.Order;
import com.vs.rest.api.BaseController;
import com.vs.service.search.ISearchService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by GeetaKrishna on 9/27/2016.
 */
@Component
@Path("/admn/search")
@Slf4j
@Api(value = "/admn/search", description = "Admin Search Controller")
public class AdminSearchController extends BaseController{

    ISearchService searchService;

    @GET
    @Path("/{userName}")
    public Response getMenusByUser(@PathParam("userName") String userName) {
       return  null;
    }
}
