package com.vs.repository;

import com.vs.model.enums.Role;
import com.vs.model.user.Cook;
import com.vs.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */

public interface UserRepository extends MongoRepository<User, String> {

    public long countByRole(Role role);

    public List<User> findByRole(Role role);

    public boolean exists(String userName);

    public User findOne(String id);

    public List<User> findByLastNameOrFirstNameOrUserNameAndRole(String lname, String fname, String uname, Role role);

    public List<User> findByLastNameOrFirstNameOrUserName(String lname, String fname, String uname);

    List<User> findByFirstName(String fName, Role role);
}
