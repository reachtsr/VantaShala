package com.vs.rest.api.user;

import com.vs.model.enums.Role;
import com.vs.model.enums.UserStatusEnum;
import com.vs.model.user.Cook;
import com.vs.model.user.User;
import com.vs.rest.api.BaseController;
import com.vs.service.user.IUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Value;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/14/2015.
 */

@Slf4j
public abstract class UserController extends BaseController {

    @Value("${vs.uploads.location}")
    protected static String UPLOAD_LOCATION;

    IUserService userService;

    public UserController(IUserService service) {
        this.userService = service;
    }

    // List Specific type of usres
    public Response listUsers(Role role) {
        log.info(" Listing all cooks ");
        List<User> users = userService.listUsers(role);
        return buildResponse(users);
    }

    // List all Users
    public Response listUsers() {
        log.info(" Listing all cooks ");
        List<User> users = userService.listUsers();
        return buildResponse(users);
    }

    public Response findUserBySearchString(String search, Role role) {
        log.info(" Searching Cook based on: {}", search);
        List<User> users = userService.findUser(search, role);
        log.info("No of Users found: {}", users.size());
        return buildResponse(users);
    }

    public Response findUserBySearchString(String search) {
        log.info(" Searching Cook based on: {}", search);
        List<User> users = userService.findUser(search);
        log.info("No of Users found: {}", users.size());
        return buildResponse(users);
    }

    public Response getCookByKitchenName(String kitchenName) {
        log.info(" Retrieving Cook: {}", kitchenName);
        Cook user = userService.getUserByKitchenName(kitchenName);
        log.info("UserDetails : {}", user);
        return buildResponse(user);

    }

    public Response getUserByUserName(String userName) {
        log.info(" Retrieving Cook: {}", userName);
        User user = userService.getUserByUserName(userName);
        log.info("UserDetails : {}", user);
        return buildResponse(user);
    }

    public Response createUser(User user) {

        log.info("Create User - User Details: {}", user);

            userService.createUser(user);
            return Response.status(200).build();

    }

    public void updateUser(String userName, User user) {
        log.info("Update Cook: {} - Cook Details: {}", userName, user);
        userService.updateUser(user);
    }

    public void enableOrDisableUser(String userName, UserStatusEnum userStatusEnum) {

        log.info("Delete Cook : {}", userName);
        userService.enableOrDisableUser(userName, userStatusEnum);
    }


    public Response addImages(String userName, RequestContext request) {

        String status = "File Uploaded.";
        if (ServletFileUpload.isMultipartContent(request)) {
            final FileItemFactory factory = new DiskFileItemFactory();
            final ServletFileUpload fileUpload = new ServletFileUpload(factory);
            try {
                /*
                 * parseRequest returns a list of FileItem
                 * but in old (pre-java5) style
                 */
                final List items = fileUpload.parseRequest(request);

                if (items != null) {
                    final Iterator iter = items.iterator();
                    while (iter.hasNext()) {
                        final FileItem item = (FileItem) iter.next();
                        final String itemName = item.getName();
                        final String fieldName = item.getFieldName();
                        final String fieldValue = item.getString();

                        if (item.isFormField()) {

                            System.out.println("Field Name: " + fieldName + ", Field Value: " + fieldValue);
                            System.out.println("Candidate Name: " + fieldValue);
                        } else {
                            final File savedFile = new File(UPLOAD_LOCATION + File.separator
                                    + itemName);
                            System.out.println("Saving the file: " + savedFile.getName());
                            item.write(savedFile);
                        }

                    }
                }
            } catch (FileUploadException fue) {
                status = fue.getMessage();
                fue.printStackTrace();
            } catch (Exception e) {
                status = e.getMessage();
                e.printStackTrace();
            }
        }
        return Response.status(200).entity(status).build();
    }

    public Response getAllUserCount() {
        return Response.status(200).entity(userService.getUserCount()).build();
    }

    public Response getUserCount(Role role) {
        return Response.status(200).entity(userService.getUserCount(role)).build();
    }
}
