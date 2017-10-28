package com.vs.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.vs.model.AddNewFiledsToCollection;
import com.vs.model.email.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
        mongoOperations.updateFirst(new Query(Criteria.where("id").is(email.get_id())),
                Update.update("status", email.getStatus()), Email.class);
    }
    public void addFieldsToCollection(AddNewFiledsToCollection addNewFiledsToCollection){

        addNewFiledsToCollection.validate();

        String id = addNewFiledsToCollection.getId();
        Map<String, String> map = addNewFiledsToCollection.getKeyValues();
        String collectionName = addNewFiledsToCollection.getCollectionType();

        BasicDBObject toAdd=new BasicDBObject();
        BasicDBObject q=new BasicDBObject("id",addNewFiledsToCollection.getId());

        toAdd.putAll(addNewFiledsToCollection.getKeyValues());

        mongoTemplate.getCollection(collectionName).update(q, new BasicDBObject("$set",toAdd) );

    }


    public List<DBObject> queryBySubDocumentId(String collectionName, String key, String matchId){
        DBCollection coll = mongoTemplate.getCollection(collectionName);
        BasicDBObject query = new BasicDBObject();
        query.put(key, new BasicDBObject("$eq", matchId));
        DBCursor cur = coll.find(query);
        return cur.toArray();
    }

}
