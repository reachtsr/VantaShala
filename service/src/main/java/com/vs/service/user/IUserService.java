package com.vs.service.user;

import com.vs.model.enums.Role;
import com.vs.model.user.Cook;
import com.vs.model.user.User;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/21/2015.
 */


public interface IUserService {

    // Common
    public void createUser(User user);
    public void updateUser(User user);
    public void disableUser(String user);
    public User getUserByUserName(String name);
    public List<User> listUsers();
    public List<User> listUsers(Role role);
    public long getUserCount();
    public int getUserCount(Role role);
    public List<User> findUser(String searchString);
    public List<User> findUser(String searchString, Role role);


    // COOK
    public Cook getUserByKitchenName(String kitchenName);

}
