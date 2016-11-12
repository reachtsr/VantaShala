package com.vs.repository;

import com.mongodb.BasicDBObject;
import com.vs.common.filters.AppConstants;
import com.vs.model.AddNewFiledsToCollection;
import com.vs.model.email.Email;
import com.vs.model.enums.EmailStatus;
import com.vs.model.enums.ItemStatus;
import com.vs.model.menu.Menu;
import jersey.repackaged.com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;

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
    public void addFieldsToCollection(AddNewFiledsToCollection addNewFiledsToCollection){

        addNewFiledsToCollection.validate();

        String id = addNewFiledsToCollection.getId();
        Map<String, String> map = addNewFiledsToCollection.getKeyValues();
        String collectionName = addNewFiledsToCollection.getCollectionType();

        BasicDBObject toAdd=new BasicDBObject();
        BasicDBObject q=new BasicDBObject("_id",addNewFiledsToCollection.getId());

        toAdd.putAll(addNewFiledsToCollection.getKeyValues());

        mongoTemplate.getCollection(collectionName).update(q, new BasicDBObject("$set",toAdd) );

    }
    public void addFiledsToItemCollection(String menuId, String itemId, AddNewFiledsToCollection addNewFiledsToCollection){

        String newField = "items.$."+ AppConstants.MENU_ITEM_PICTURE;

        mongoTemplate.findAndModify(
                Query.query(where("_id").is(menuId).
                        and("items._id").is(itemId)), update(newField, addNewFiledsToCollection.getKeyValues().get(AppConstants.MENU_ITEM_PICTURE)), Menu.class
        );

    }

    public void updateUserMenuItemStatus(String menuId, String itemId, ItemStatus status) {

        mongoTemplate.findAndModify(
                Query.query(where("_id").is(menuId).
                        and("items._id").is(itemId)), update("items.$.status", status), Menu.class
        );

    }

}
