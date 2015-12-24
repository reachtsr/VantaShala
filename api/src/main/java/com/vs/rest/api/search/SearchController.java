package com.vs.rest.api.search;

import com.vs.model.user.Cook;
import com.vs.model.user.User;
import com.vs.service.search.ISearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Component
@Path("/search")
@Slf4j
public class SearchController {

    @Autowired
    ISearchService searchService;

    @Path("/{zipCode}")
    public void searchForCooksByZipCode(@PathParam("zipCode") String zipCode){

        List<Cook> users = new ArrayList<>();

        try {
            users = searchService.findByBusinessAddressZipCodeRadius(zipCode);
        }
        catch (NumberFormatException nfe) {

            Response.status(200).entity("Invalid ZipCode").build();
        }
        Response.status(200).entity(users).build();
    }
}
