package com.vs.service.user;

import com.vs.model.enums.Role;
import org.springframework.stereotype.Service;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */
@Service
public class CustomerService extends UserServiceImpl {

    public CustomerService() throws Exception {
        super(Role.CUSTOMER);
    }
}
