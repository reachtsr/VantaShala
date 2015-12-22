package com.vs.service.user;

import com.vs.model.enums.Role;
import com.vs.model.user.User;
import com.vs.service.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */
@Service
public class UserService implements IUserService
{

    @Autowired
    UserRepository userRepository;

    public void saveCook(User user){
        user.setRole(Role.COOK);
        userRepository.save(user);
    }

    @Override
    public void saveCustomer(User user) {
        user.setRole(Role.CUSTOMER);
        userRepository.save(user);
    }

    public User getCook(String firstName){
        return userRepository.findByFirstName(firstName);
    }

    public User getCookByKitchenName(String kitchenName){
        return userRepository.findByKitchenName(kitchenName);
    }
}
