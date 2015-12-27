package com.vs.repository;

import com.vs.model.email.Email;
import com.vs.model.enums.EmailStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * Created by GeetaKrishna on 12/26/2015.
 */
@Component
public class DBOperations {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void updateEmailStatus(Email email){
        mongoOperations.updateFirst(new Query(Criteria.where("_id").is(email.get_id())),
                Update.update("status", email.getStatus()), Email.class);
    }

}
