package com.vs.service.user;

import com.vs.model.user.User;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/21/2015.
 */


public interface IUserService {

    public void saveUser(User user);
    public List<User> listUsers();
    public List<User> getUserByFirstName(String name);
    public List<User> getUserByLastName(String name);
    public User getUserByKitchenName(String name);
    public User getUserByUserName(String name);
    public List<User> searchUser(String name);
    public int getUserCountByKitchenName(String name);
    public int getUserCountByFirstName(String name);
    public int getUserCountByLastName(String name);

}
