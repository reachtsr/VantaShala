package com.vs.rest.api.user;

import com.vs.model.enums.FileUploadTypeEnum;
import com.vs.model.user.Cook;
import com.vs.service.user.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */
@Controller
@Path("/cook")
@Slf4j
@Api(value = "/cook", description = "Cook Controller")
public class CookController extends UserController {

    @Context
    private HttpServletRequest request;
    @Context
    private HttpServletResponse response;

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


    @POST
    public Response createCook(Cook user) throws Exception {
        return super.createUser(user);
    }

    @ApiOperation(value = "Upload Image", nickname = "uploadImage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Failure")})


    @POST
    @Path("/upload/profile/{userName}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadPdfFile(@PathParam("userName") String userName,@FormDataParam("file") InputStream file,
                                  @FormDataParam("file") FormDataContentDisposition fileDisposition) throws Exception {
        log.info("TEST");
        saveFile(userName, FileUploadTypeEnum.PROFILE_PICTURE, file, fileDisposition);
        return Response.status(200).build();

    }


    @ApiOperation(value = "Update Cook", nickname = "updateCook")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Failure")})

    @PUT
    @Path("/{userName}")
    public void updateCook(@PathParam("userName") String userName, Cook user) {
        super.updateUser(userName, user);
    }

    @ApiOperation(value = "Find Cook by Kitchen Name", response = Cook.class, nickname = "kitchName")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Failure")})
    @GET
    @Path("/kitchenName/{kitchenName}")
    public Response getCook(@PathParam("kitchenName") String kitchenName) {
        return super.getCookByKitchenName(kitchenName);
    }
}
