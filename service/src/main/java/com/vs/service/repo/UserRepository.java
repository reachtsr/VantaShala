package com.vs.service.repo;

import com.vs.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */

public interface UserRepository extends MongoRepository<User, String>{

    public User findByFirstName(String firstName);
    public List<User> findByLastName(String lastName);
    public User findByKitchenName(String kitchenName);
}
