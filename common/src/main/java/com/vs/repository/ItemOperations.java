package com.vs.repository;

import com.vs.common.filters.AppConstants;
import com.vs.model.AddNewFiledsToCollection;
import com.vs.model.enums.ItemStatus;
import com.vs.model.menu.Item;
import com.vs.model.menu.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

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

    public void addPictureTotem(String menuId, String itemId, AddNewFiledsToCollection addNewFiledsToCollection){

        String newField = "items.$."+ AppConstants.MENU_ITEM_PICTURE;

        mongoTemplate.findAndModify(
                Query.query(where("_id").is(menuId).
                        and("items._id").is(itemId)), update(newField, addNewFiledsToCollection.getKeyValues().get(AppConstants.MENU_ITEM_PICTURE)), Menu.class
        );
    }

    public void addNewItemToMenu(String menuId, Item item){

        Query query = Query.query(where("_id").is(menuId));
        Update update = new Update().addToSet("items.$", item);
        mongoTemplate.findAndModify( query, update, Menu.class);
    }

    public void updateExistingItem(String menuId, Item item){

        mongoTemplate.findAndModify(
                Query.query(where("_id").is(menuId).
                        and("items._id").is(item.getId())), update("items.$", item), Menu.class
        );

    }

    public void deleteExistingItem(String menuId, String itemId){

        Query query = Query.query(where("_id").is(menuId));
        Update update = new Update().pull("items.$", itemId);
        mongoTemplate.findAndModify( query, update, Menu.class);

    }

    public void updateUserMenuItemStatus(String menuId, String itemId, ItemStatus status) {

        mongoTemplate.findAndModify(
                Query.query(where("_id").is(menuId).
                        and("items._id").is(itemId)), update("items.$.status", status), Menu.class
        );

    }


}
