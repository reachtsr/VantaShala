package com.vs.service.user;

import com.vs.model.enums.Role;
import com.vs.model.user.User;
import com.vs.service.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */
public class CustomerService extends UserServiceImpl{

    public CustomerService(Role role) throws Exception {
        super(role);
    }


}
