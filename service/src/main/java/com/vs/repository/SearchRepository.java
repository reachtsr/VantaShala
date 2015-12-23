package com.vs.repository;

import com.vs.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
public interface SearchRepository extends MongoRepository<User, String> {


    public List<User> findByBusinessAddressZipCode(String zipcode);

    public List<User> findByBusinessAddressZipCodeBetween(long zipcodeMin, long zipcodeMax);

}
