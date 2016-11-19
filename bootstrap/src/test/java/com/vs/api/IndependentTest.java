package com.vs.api;

import com.mongodb.MongoClient;
import com.vs.model.enums.ItemStatus;
import com.vs.model.enums.Role;
import com.vs.model.menu.Item;
import com.vs.model.menu.Menu;
import com.vs.model.user.Cook;
import com.vs.repository.CookRepository;
import com.vs.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Created by gopi on 10/23/16.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringMongoConfiguration.class})
@Slf4j
@ComponentScan(basePackages = "com.vs")
public class IndependentTest {

    @Autowired
    private MenuRepository menuRepository;


    @Autowired
    private CookRepository cookRepository;

    @Autowired
    private MongoTemplate template;

    @Test
    public void m5() {

        ArrayList l = new ArrayList();
        l.add("d3d01414-c416-431a-ab08-fc650054598f");
      Menu item = menuRepository.findByMenuIdAndItems_Id("e0fc5f20-86e1-456b-9515-b411c576c62f","d3d01414-c416-431a-ab08-fc650054598f");
      log.info("List: {}", item);
    }

    public void m2() {

        List<Cook> cook = cookRepository.findByKitchenName("KITCHEN_b9044429-7e1d-4bc2-a69e-358cf23fa237", Role.COOK);
        log.info("===>{} - {}", cook.size(), cook);
    }

    public void m4() {

//        String collectionName = Menu.class.getSimpleName().toLowerCase();
//
        String menuId = "39e6eb9a-38b9-4acf-9ee0-39ff30b960e5";
        String itemId = "f4c6a141-deb4-44dd-a127-7d040cf4d095";
//
//        BasicDBObject toAdd=new BasicDBObject();
//        BasicDBObject q=new BasicDBObject("_id",menuId).append("items._id", itemId);
//
//        DBCursor cursor =  template.getCollection(collectionName).find(q);
//        DBCursor cursor1 =  template.getCollection("items").find(q);
//        while (cursor.hasNext()) {
//            System.out.println(cursor.next());
//        }
//        while (cursor1.hasNext()) {
//            System.out.println(cursor1.next());
//        }
//        toAdd.put(AppConstants.PROFILE_PICTURE,"gopi.jpg");
//
//        template.getCollection(collectionName).update(q, new BasicDBObject("$set",toAdd), true, false);

        template.findAndModify(
                Query.query(where("_id").is(menuId).
                        and("items._id").is(itemId)), update("items.$.profilePicture", "gopi.jpg"), Menu.class
        );
    }

    public void m3() {


        template.findAndModify(
                Query.query(where("_id").is("f972c125-792d-4f6d-b74b-ca8776da84a9").
                and("items._id").is("87a6b16b-db9f-4827-9ccd-7dc6d25918d1")), update("items.$.status", ItemStatus.LOCKED), Menu.class
        );




//        MongoConverter converter = template.getConverter();
//        Query query = Query.query(Criteria.where("_id").is("f972c125-792d-4f6d-b74b-ca8776da84a9").and("items._id").is("87a6b16b-db9f-4827-9ccd-7dc6d25918d1"));
//        query.fields().include("items._id");
//
//        Item item = new Item();// template.findOne(query, Item.class);
//        item.setStatus(ItemStatus.LOCKED);
//        DBObject newSectionRec = (DBObject) converter.convertToMongoType(item);
//
//
//        Update update = new Update().addToSet("items.$.status", newSectionRec);
//        template.findAndModify(query, update, Item.class);
//


//
//        UUID id = UUID.fromString("5b5d4740-eb05-4242-8dd7-494bb9569fac");
//        Item item = new Item();
//        item.setStatus(ItemStatus.LOCKED);
//
//        Query query = Query.query(Criteria.where("_id").is(id.toString()));
//
//        Update update = new Update();
//        update.addToSet("items", item);
//
//        this.template.findAndModify(query, update, Menu.class);

    }


    public void m1() {

        boolean exists = menuRepository.exists(("b7470eee-c1c9-a393-3b0b-842cefe42299"));
        log.info("===>{} - {}", exists, null);
    }
}

@EnableMongoRepositories({"com.vs.repository"})
class SpringMongoConfiguration {

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(new MongoClient("localhost"), "VantaShala");
    }

}