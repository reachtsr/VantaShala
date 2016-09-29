package com.vs.repository;

import com.vs.model.user.Cook;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
public interface CookRepository extends MongoRepository<Cook, String> {

    int countByKitchenName(String name);

    //@Query("{ 'kitchenName' : ?0 }")
    List<Cook> findByKitchenName(String kitchenName);

    //public List<User> findByLastNameOrFirstNameOrKitchenNameOrUserNameAndRole(String lname, String fname,String kname,String uname, Role role);
}
