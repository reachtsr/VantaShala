package com.vs.rest.api.user.search;

import com.vs.model.enums.UserTypeEnum;
import com.vs.rest.api.user.CustomerController;
import com.vs.rest.api.user.UserController;
import com.vs.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by GeetaKrishna on 9/11/2016.
 */
@Component
@Path("/search")
@Slf4j
public class SearchController extends UserController {


    @Autowired
    public SearchController(@Qualifier IUserService userService) {
        super(userService);
    }

    @PostConstruct
    public void init() {
        log.info("{} Initiated.", this.getClass().getName());
    }

    @GET
    @Path("cook/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response searchCook(@PathParam("name") String name) {
        return super.searchUser(name, UserTypeEnum.COOK);
    }

    @GET
    @Path("customer/{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response searchCustomer(@PathParam("name") String name) {
        return super.searchUser(name, UserTypeEnum.CUSTOMER);
    }


}
