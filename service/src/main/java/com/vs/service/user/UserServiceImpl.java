package com.vs.service.user;

import com.vs.model.enums.Role;
import com.vs.model.user.User;
import com.vs.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */
@Slf4j
public abstract class UserServiceImpl implements IUserService {

    private Role role = null;

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(Role role) throws Exception {
        this.role = role;
        if (this.role == null) {
            throw new Exception("UserService - Role is NULL ");
        }
        log.info("Role Assigned: {}", role.name());
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
    public List<User> getUserByKitchenName(String name) {
        return userRepository.findByKitchenName(name);
    }

    @Override
    public List<User> getUserByUserName(String name) {
        return userRepository.findByUserName(name);
    }

    @Override
    public List<User> searchUser(String name) {
        return userRepository.findByLastNameOrFirstNameOrKitchenNameOrUserNameAndRole(name, name,name,name,role);
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

    @Override
    public int getUserCount() {
        return userRepository.countByRole(role);
    }
}
