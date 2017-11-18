package com.vs.rest.api.search;

import com.vs.model.user.Cook;
import com.vs.rest.api.BaseController;
import com.vs.service.search.ISearchService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Component
@Path("/search")
@Slf4j
@Api(value = "/search", description = "Big Data Search")
public class SearchController extends BaseController {

    ISearchService searchService;

    @PostConstruct
    public void init() {
        log.info("{} Initiated.", this.getClass().getName());
    }

    @GET
    @Path("cooks/{zip}")

    public Response findCooksByZIP(@PathParam("zip") String zip) {
        List<Cook> users = searchService.findCooksByZIP(zip);
        return Response.status(200).entity(users).build();
    }

    @GET
    @Path("customers/{zip}")

    public Response findCustomersByZIP(@PathParam("zip") String zip) {
        List<Cook> users = searchService.findCooksByZIP(zip);
        return Response.status(200).entity(users).build();
    }

    @GET
    @Path("cooks/currentLocation/{zip}")

    public Response findCooksByCurrentLocation(@PathParam("zip") String zip) {
        List<Cook> users = searchService.findCooksByCurrentLocation(zip);
        return Response.status(200).entity(users).build();
    }

    @GET
    @Path("customers/currentLocation/{zip}")

    public Response findCustomersByCurrentLocation(@PathParam("zip") String zip) {
        List<Cook> users = searchService.findCustomersByCurrentLocation(zip);
        return Response.status(200).entity(users).build();
    }
}
