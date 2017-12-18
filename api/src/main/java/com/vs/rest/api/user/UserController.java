package com.vs.rest.api.user;

import com.google.common.base.Preconditions;
import com.mongodb.DuplicateKeyException;
import com.vs.common.errorHandling.CustomReasonPhraseException;
import com.vs.model.enums.Role;
import com.vs.model.enums.UserStatusEnum;
import com.vs.model.props.ReadYML;
import com.vs.model.user.User;
import com.vs.rest.api.BaseController;
import com.vs.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GeetaKrishna on 12/14/2015.
 */

@Slf4j
public abstract class UserController extends BaseController {

    protected IUserService userService;

    @Autowired
    protected ReadYML readYML;

    public UserController(IUserService service) {
        this.userService = service;
    }

    // List Specific type of users
    public Response listUsers(Role role) {
        log.info(" Listing all cooks ");
        List<User> users = userService.listUsers(role);
        return build200Response(users);
    }

    // List all Users
    public Response listUsers() {
        log.info(" Listing all cooks ");
        List<User> users = userService.listUsers();
        return build200Response(users);
    }

    public Response findUserBySearchString(String search, Role role) {
        log.info(" Searching User based on: {}", search);
        List<User> users = userService.findUser(search, role);
        log.info("No of Users found: {}", users.size());
        return build200Response(users);
    }

    public Response findUserBySearchString(String search) {
        log.info(" Searching User based on: {}", search);
        List<User> users = userService.findUser(search);
        log.info("No of Users found: {}", users.size());
        return build200Response(users);
    }

    public Response getCookByKitchenName(String kitchenName) {
        log.info(" Retrieving User: {}", kitchenName);
        User user = userService.getUserByKitchenName(kitchenName);
        log.info("UserDetails : {}", user);
        return build200Response(user);
    }

    // @Todo send an email when a cook pub
    public Response subscribeCustomerToCook(String cookId, String customerId) {

        boolean status = userService.subscribeCustomerToCook(cookId, customerId);
        Preconditions.checkState(status);
        return buildOKResponse(status);
    }

    public Response getSubscriptions(String userId, Role role) {

        List<User> list = userService.getSubscriptions(userId, role);
        return build200Response(list);

    }

    public Response findUserByFirstName(String name, Role role) {
        log.info(" Searching User by FirstName : {}", name);

        if (role == Role.COOK) {
            List<User> users = userService.getCookByFirstName(name);
            return build200Response(users);
        } else {
            List<User> users = userService.getCustomerByFirstName(name);
            return build200Response(users);
        }
    }

    public Response getUserByUserName(String userName) {
        log.info(" Retrieving User: {}", userName);
        User user = userService.getUserByUserName(userName);
        log.info("UserDetails : {}", user);
        return build200Response(user);
    }

    public Response createUser(User user) throws Exception {

        try {
            log.info("Create User - User Details: {}", user);
            userService.createUser(user);
            return build201Response(user);
        } catch (DuplicateKeyException exception) {
            CustomReasonPhraseException customReasonPhraseException = new CustomReasonPhraseException(Response.Status.BAD_REQUEST, exception.getMessage());
            throw customReasonPhraseException;
        }


    }

    public Response updateUser(String userName, User user) {
        log.info("Update User: {} - User Details: {}", userName, user);
        return build200Response(userService.updateUser(user));
    }

    public void enableOrDisableUser(String userName, UserStatusEnum userStatusEnum) throws Exception {

        log.info("Disabling User : {}", userName);
        userService.enableOrDisableUser(userName, userStatusEnum);
    }

    public Response getAllUserCount() {
        long count = userService.getUserCount();
        return Response.status(200).entity(getCountMap(count)).build();
    }

    public Response getUserCount(Role role) {
        long count = userService.getUserCount(role);
        return Response.status(200).entity(getCountMap(count)).build();
    }

    private Map<String, Long> getCountMap(long count) {
        Map<String, Long> map = new HashMap<>();
        map.put("count", count);
        return map;
    }

    private Map<String, String> getStatusMap(String count) {
        Map<String, String> map = new HashMap<>();
        map.put("status", count);
        return map;
    }
}
