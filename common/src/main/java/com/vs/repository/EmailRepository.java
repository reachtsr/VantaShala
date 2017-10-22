package com.vs.repository;

import com.vs.model.email.Email;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
@Document(collection = "email")
public interface EmailRepository extends MongoRepository<Email, String> {


}
