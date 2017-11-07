package com.vs.repository;

import com.mongodb.BasicDBObject;
import com.vs.common.filters.AppConstants;
import com.vs.model.AddNewFiledsToCollection;
import com.vs.model.enums.ItemStatus;
import com.vs.model.menu.Item;
import com.vs.model.menu.Menu;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by GeetaKrishna on 12/26/2015.
 */
@Component
public class ItemOperations {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private MongoTemplate mongoTemplate;

    public void addPictureTotem(ObjectId menuId, ObjectId itemId, String path) {

        Update mergeUserUpdate = new Update();
        mergeUserUpdate.set("items.$.picture", path);

        mongoTemplate.findAndModify(Query.query(where("id").is(menuId).
                and("items.id").is(itemId)), mergeUserUpdate, Menu.class);

    }

    public void addNewItemToExistingItems(ObjectId menuId, List<Item> items) {

        Update update = new Update();
        update.pushAll("items", items.toArray());
        //update.addToSet("items", items);
        Criteria criteria = Criteria.where("_id").is(menuId);
        mongoTemplate.updateFirst(Query.query(criteria), update, Menu.class);
    }

    public void addNewItemToMenu(ObjectId menuId, List<Item> item) {

        Query query = Query.query(where("id").is(menuId));
        Menu menu = mongoTemplate.find(query, Menu.class).get(0);
        menu.setItems(item);
        mongoOperations.save(menu);

    }


    public void updateExistingItem(ObjectId menuId, Item item) {

        mongoTemplate.findAndModify(
                Query.query(where("id").is(menuId).
                        and("items.id").is(item.getId())), update("items.$", item), Menu.class
        );

    }

    public void deleteExistingItem(ObjectId menuId, ObjectId itemId) {

        Update update = new Update();
        update.pull("items", new BasicDBObject("_id", itemId));
        Criteria criteria = Criteria.where("_id").is(menuId);
        mongoTemplate.upsert(Query.query(criteria), update, Menu.class);

//        Query query = Query.query(where("id").is(menuId));
//        Update update = new Update().pull("items.$", itemId);
//        mongoTemplate.findAndModify( query, update, Menu.class);

    }

    public void updateUserMenuItemStatus(ObjectId menuId, ObjectId itemId, ItemStatus status) {

        mongoTemplate.findAndModify(
                Query.query(where("id").is(menuId).
                        and("items.id").is(itemId)), update("items.$.status", status), Menu.class
        );

    }


}
