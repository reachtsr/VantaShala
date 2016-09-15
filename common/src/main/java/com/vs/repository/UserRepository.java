package com.vs.repository;

import com.vs.model.enums.Role;
import com.vs.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */

public interface UserRepository extends MongoRepository<User, String> {

    public int countByRole(Role role);

    public List<User> findByRole(Role role);

    public boolean exists(String userName);

    public List<User> findByUserName(String userName);

    public List<User> findByLastNameOrFirstNameOrUserNameAndRole(String lname, String fname, String uname, Role role);

    public List<User> findByLastNameOrFirstNameOrUserName(String lname, String fname, String uname);
}
