package com.vs.service.user;

import com.vs.model.enums.Role;
import org.springframework.stereotype.Service;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */
@Service
public class AdminService extends UserServiceImpl {

    public AdminService() throws Exception {
        super(Role.ADMIN);
    }

//    public List<User> searchCook(String name) {
//        return getCookRepository().findByLastNameOrFirstNameOrKitchenNameOrUserNameAndRole(name, name,name,name,super.getRole());
//    }

}
