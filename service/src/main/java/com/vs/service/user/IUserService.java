package com.vs.service.user;

import com.vs.model.SaveFileModel;
import com.vs.model.enums.Role;
import com.vs.model.enums.UserStatusEnum;
import com.vs.model.user.User;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/21/2015.
 */


public interface IUserService {

    public void createUser(User user) throws Exception;

    public void updateUser(User user);

    public void enableOrDisableUser(String user, UserStatusEnum userStatusEnum) throws Exception;

    public User getUserByUserName(String name);

    public List<User> listUsers();

    public List<User> listUsers(Role role);

    public long getUserCount();

    public long getUserCount(Role role);

    public List<User> findUser(String searchString);

    public List<User> findUser(String searchString, Role role);

    public User getUserByKitchenName(String kitchenName);

    public List<User> getCookByFirstName(String name);

    public List<User> getCustomerByFirstName(String name);

    public String saveFile(String userName, SaveFileModel saveFile);

}
