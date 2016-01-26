package com.auth.repository;

import com.auth.model.MongoConnection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by GeetaKrishna on 1/2/2016.
 */
@Repository
public interface UserSocialConnectionRepository extends MongoRepository<MongoConnection, String> {
    List<MongoConnection> findByUserId(String userId);

    List<MongoConnection> findByUserIdAndProviderId(String userId, String providerId);

    List<MongoConnection> findByProviderIdAndProviderUserId(String providerId, String providerUserId);

    MongoConnection findByUserIdAndProviderIdAndProviderUserId(String userId, String providerId, String providerUserId);

    List<MongoConnection> findByProviderIdAndProviderUserIdIn(String providerId, Collection<String> providerUserIds);
}
