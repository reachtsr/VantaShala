package com.vs.rest.api.user;

import com.vs.model.SaveFileModel;
import com.vs.model.enums.FileUploadTypeEnum;
import com.vs.model.enums.Role;
import com.vs.model.user.Cook;
import com.vs.service.user.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jersey.repackaged.com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
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

    @ApiOperation(value = "Find Cook", nickname = "FindCook")
    @GET
    public Response getCook(@HeaderParam("userName") String userName) {
        Preconditions.checkNotNull(userName, "User Not found:" + userName);
        return super.getUserByUserName(userName);
    }

    @ApiOperation(value = "Create Cook", nickname = "createCook")
    @POST
    public Response createCook(Cook user) throws Exception {
        return super.createUser(user);
    }

    @ApiOperation(value = "Upload Image", nickname = "uploadImage")
    @POST
    @Path("/upload/profile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadProfilePicture(@HeaderParam("userName") String userName, @FormDataParam("file") InputStream file,
                                         @FormDataParam("file") FormDataContentDisposition fileDisposition) throws Exception {
        log.info("Uploading UserProfile Pic");

        //Preconditions.checkArgument(fileDisposition.getSize() > AppConstants.MAX_PROFILE_SIZE);
        SaveFileModel saveFile = new SaveFileModel();
        saveFile.setContentDisposition(fileDisposition);
        saveFile.setInputStream(file);
        saveFile.setFileUploadTypeEnum(FileUploadTypeEnum.PROFILE_PICTURE);
        saveFile.validate();

        userService.saveFile(userName, saveFile);

        return Response.status(200).build();

    }

    @ApiOperation(value = "Update Cook", nickname = "updateCook")
    @PUT
    public Response updateUser(@NotNull @HeaderParam("userName") String userName, @NotNull Cook user) {
        return super.updateUser(userName, user);
    }

    @ApiOperation(value = "Find Cook by Kitchen Name", response = Cook.class, nickname = "kitchenName")
    @GET
    @Path("/kitchenName/{kitchenName}")
    public Response getCookByKitcheName(@PathParam("kitchenName") String kitchenName) {
        return super.getCookByKitchenName(kitchenName);
    }

    @ApiOperation(value = "Get Subscription for Cook", nickname = "getsubscriptionsforcook")
    @GET
    @Path("/subscriptions")
    public Response getSubscriptions(@HeaderParam("userName") String userName) {
        return super.getSubscriptions(userName, Role.COOK);
    }


}
