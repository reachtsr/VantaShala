package com.vs.repository;

import com.vs.model.email.Email;
import com.vs.model.user.Cook;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
public interface EmailRepository extends MongoRepository<Email, String> {


}
