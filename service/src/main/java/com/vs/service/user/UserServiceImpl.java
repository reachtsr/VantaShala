package com.vs.service.user;

import com.vs.model.enums.Role;
import com.vs.model.user.User;
import com.vs.service.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */

public abstract class UserServiceImpl implements IUserService{

    private Role role = null;

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(Role role) throws  Exception{
        this.role = role;
        if(this.role == null) {
            throw new Exception("UserService - Role is NULL ");
        }
    }

    @Override
    public void saveUser(User user) {
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findByRole(role.name());
    }

    @Override
    public List<User> getUserByFirstName(String name) {
        return userRepository.findByFirstNameAndRole(name, role);
    }

    @Override
    public List<User> getUserByLastName(String name) {
        return userRepository.findByLastNameAndRole(name, role);
    }

    @Override
    public User getUserByKitchenName(String name) {
        return userRepository.findByKitchenName(name);
    }

    @Override
    public User getUserByUserName(String name) {
        return userRepository.findByUserName(name);
    }

    @Override
    public List<User> searchUser(String name) {
        List<User> fUsers = getUserByFirstName(name);
        List<User> lUsers = getUserByLastName(name);
        User user = getUserByKitchenName(name);

        List<User> users = new ArrayList<>();
        users.addAll(lUsers);
        users.addAll(fUsers);
        users.add(user);

        return users;
    }

    @Override
    public int getUserCountByKitchenName(String name) {
        return userRepository.countByKitchenName(name);
    }

    @Override
    public int getUserCountByFirstName(String name) {
        return userRepository.countByFirstNameAndRole(name, role);
    }

    @Override
    public int getUserCountByLastName(String name) {
        return userRepository.countByLastNameAndRole(name, role);
    }

}
