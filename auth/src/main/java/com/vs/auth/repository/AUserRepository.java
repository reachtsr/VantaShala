package com.vs.auth.repository;

import com.vs.model.enums.Role;
import com.vs.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */
@Repository
public interface AUserRepository extends MongoRepository<User, String> {

    public List<User> findByFirstName(String firstName);
    public User findByUsername(String userName);
    public List<User> findByFirstNameAndRole(String firstName, Role role);
    public List<User> findByLastNameAndRole(String firstName, Role role);

    public User findByEmail(String email);
    public List<User> findByLastName(String lastName);

    public int countByLastName(String name);
    public int countByFirstName(String name);
    public int countByLastNameAndRole(String name, Role role);
    public int countByFirstNameAndRole(String name, Role role);
    public int countByRole(Role role);
    public List<User> findByRole(String roleName);
    public boolean exists(String userName);
   // public List<UserProfile> findByUserProfileName(String userName);
   // public List<UserProfile> findByLastNameOrFirstNameOrUserProfileNameAndRole(String lname, String fname, String uname, Role role);
}
