package com.vs.repository;

import com.vs.model.email.Email;
import com.vs.model.menu.Menu;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
@Document(collection = "email")
public interface EmailRepository extends MongoRepository<Email, String> {

    public List<Email> findByStatus(String status);


}
