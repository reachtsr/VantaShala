package com.vs.repository;

import com.vs.model.user.CustomerCookSubscription;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by gopi on 11/19/2016.
 */
public interface SubscriptionRepository extends MongoRepository<CustomerCookSubscription, String> {
    public CustomerCookSubscription findByUserId(String userId);
}
