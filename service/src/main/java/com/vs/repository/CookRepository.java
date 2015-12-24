package com.vs.repository;

import com.vs.model.user.Cook;
import com.vs.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
public interface CookRepository extends MongoRepository<Cook, String> {

    public int countByKitchenName(String name);
    public List<User> findByKitchenName(String kitchenName);

}
