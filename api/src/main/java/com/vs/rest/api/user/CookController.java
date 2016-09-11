package com.vs.rest.api.user;

import com.vs.model.user.Cook;
import com.vs.model.user.User;
import com.vs.service.user.IUserService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */
@Component
@Path("/cook")
@Slf4j
public class CookController extends UserController {


    @Autowired
    public CookController(@Qualifier("cookService") IUserService userService) {
        super(userService);
    }

    @PostConstruct
    public void init() {
        log.info("{} Initiated.", this.getClass().getName());
    }

    @ApiOperation(value = "Create Cook", nickname = "createCook")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(method = RequestMethod.POST, path="/", produces = MediaType.APPLICATION_JSON)

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createCook(Cook user){
        return super.createUser(user);
    }





    @ApiOperation(value = "Upload Image", nickname = "uploadImage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(method = RequestMethod.POST, path="/upload/{userName}", produces = MediaType.APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "User's name", required = true, dataType = "string", paramType = "path")
    })

    @POST
    @Path("/upload/{userName}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImages(@PathParam("userName") String userName, @Context RequestContext request){
        return super.addImages(userName, request);
    }





    @ApiOperation(value = "Update Cook", nickname = "updateCook")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(method = RequestMethod.PUT, path="/{userName}", produces = MediaType.APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "User's name", required = true, dataType = "string", paramType = "path")
    })

    @PUT
    @Path("/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void updateCook(@PathParam("userName") String userName, Cook user){
       super.updateUser(userName, user);
    }





    @ApiOperation(value = "Disable Cook", nickname = "disableCook")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Failure")})
    @RequestMapping(method = RequestMethod.DELETE, path="/{userName}", produces = MediaType.APPLICATION_JSON)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "User's name", required = true, dataType = "string", paramType = "path")
    })

    @DELETE
    @Path("/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void disableCook(@PathParam("userName") String userName){
        super.deleteUser(userName);
    }

}
