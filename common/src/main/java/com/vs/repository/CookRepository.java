package com.vs.repository;

import com.vs.model.enums.Country;
import com.vs.model.enums.Role;
import com.vs.model.user.Cook;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
public interface CookRepository extends MongoRepository<Cook, String> {


    List<Cook> findByKitchenName(String kitchenName, Role role);
    int countByKitchenName(String name, Role role);

    List<Cook> findAllByBusinessAddress_ZipCodeAndCountry(String zipCode, Country country, Role role);

}
