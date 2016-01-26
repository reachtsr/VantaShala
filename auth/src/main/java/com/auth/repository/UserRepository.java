package com.auth.repository;

import com.auth.model.UserProfile;
import com.vs.model.enums.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */
@Repository
public interface UserRepository extends MongoRepository<UserProfile, String> {

    public List<UserProfile> findByFirstName(String firstName);
    public UserProfile findByUsername(String userName);
    public List<UserProfile> findByFirstNameAndRole(String firstName, Role role);
    public List<UserProfile> findByLastNameAndRole(String firstName, Role role);

    public UserProfile findByEmail(String email);
    public List<UserProfile> findByLastName(String lastName);

    public int countByLastName(String name);
    public int countByFirstName(String name);
    public int countByLastNameAndRole(String name, Role role);
    public int countByFirstNameAndRole(String name, Role role);
    public int countByRole(Role role);
    public List<UserProfile> findByRole(String roleName);
    public boolean exists(String userName);
   // public List<UserProfile> findByUserProfileName(String userName);
   // public List<UserProfile> findByLastNameOrFirstNameOrUserProfileNameAndRole(String lname, String fname, String uname, Role role);
}
