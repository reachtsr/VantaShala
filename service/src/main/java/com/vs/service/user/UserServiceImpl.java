package com.vs.service.user;

import com.vs.model.enums.Role;
import com.vs.model.enums.UserStatusEnum;
import com.vs.model.user.Cook;
import com.vs.model.user.User;
import com.vs.repository.CookRepository;
import com.vs.repository.UserRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */
@Slf4j
@Data
public abstract class UserServiceImpl implements IUserService {

    private Role role = null;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CookRepository cookRepository;

    public UserServiceImpl(Role role) throws Exception {
        this.role = role;
        if (this.role == null) {
            throw new Exception("UserService - Role is NULL ");
        }
        log.info("Role Assigned: {}", role.name());
    }

    @Override
    public void createUser(User user) {
        userRepository.insert(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Cook getUserByKitchenName(String name) {
        return cookRepository.findByKitchenName(name).get(0);
    }

    @Override
    public User getUserByUserName(String name) {
        return userRepository.findByUserName(name).get(0);
    }

    @Override
    public void enableOrDisableUser(String userName, UserStatusEnum userStatus) {
        userRepository.findByUserName(userName).get(0).setStatus(userStatus);
    }


    @Override
    public long getUserCount() {
        return userRepository.count();
    }

    @Override
    public int getUserCount(Role role) {
        return userRepository.countByRole(role);
    }

    @Override
    public List<User> listUsers(Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findUser(String searchString, Role role) {
        return userRepository.findByLastNameOrFirstNameOrUserNameAndRole(searchString, searchString, searchString, role);
    }

    @Override
    public List<User> findUser(String searchString) {
        return userRepository.findByLastNameOrFirstNameOrUserName(searchString, searchString, searchString);
    }


}
