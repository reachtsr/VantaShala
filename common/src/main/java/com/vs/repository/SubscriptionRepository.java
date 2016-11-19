package com.vs.repository;

import com.vs.model.user.CustomerToCookSubscription;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by gopi on 11/19/2016.
 */
public interface SubscriptionRepository extends MongoRepository<CustomerToCookSubscription, String> {
    public CustomerToCookSubscription findByCook(String cookId);
}
