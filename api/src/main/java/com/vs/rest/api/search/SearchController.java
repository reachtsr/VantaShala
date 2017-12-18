package com.vs.rest.api.search;

import com.vs.model.enums.Country;
import com.vs.model.enums.Role;
import com.vs.model.user.Cook;
import com.vs.model.user.User;
import com.vs.rest.api.BaseController;
import com.vs.service.search.ISearchService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResults;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
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

    @Autowired
    ISearchService searchService;

    @PostConstruct
    public void init() {
        log.info("{} Initiated.", this.getClass().getName());
    }

    @GET
    @Path("cooks/{country}/{zip}")
    public Response findCooksByZIP(@PathParam("country") Country country, @PathParam("zip") String zip) {

        List users = searchService.findUsersByZIP(zip, country, Role.COOK);
        return Response.status(200).entity(users).build();
    }

    @GET
    @Path("customers/{country}/{zip}")
    public Response findCustomersByZIP(@PathParam("country") Country country, @PathParam("zip") String zip) {
        List users = searchService.findUsersByZIP(zip, country, Role.CUSTOMER);
        return Response.status(200).entity(users).build();
    }

    @GET
    @Path("cooks/{country}/nearBy/loc")
    public Response findCooksByNearByLocation(@PathParam("country") Country country, @QueryParam("loc") List<Double> loc, @QueryParam("miles") int miles) {
        double[] location = new double[2];
        location[0] = loc.get(0);
        location[1] = loc.get(1);
        List<Cook> users = searchService.findUsersNearByLocation(country, location, miles);
        return Response.status(200).entity(users).build();
    }

    @GET
    @Path("cooks/{country}/nearBy/zip")
    public Response findCooksNearByZip(@PathParam("country") Country country, @QueryParam("zip") String zipCode, @QueryParam("miles") int miles) {
        List<Cook> users = searchService.findUsersNearByZipCode(country, zipCode, miles);
        return Response.status(200).entity(users).build();
    }

    @GET
    @Path("cooks/{country}/nearBy/{userId}")
    public Response findCooksNearByUser(@PathParam("country") Country country, @QueryParam("userId") String userId, @QueryParam("miles") int miles) {
        List<Cook> users = searchService.findUsersNearByUserId(country, userId, miles);
        return Response.status(200).entity(users).build();
    }

}
