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

import java.util.List;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;
import static org.springframework.data.mongodb.core.query.Query.query;

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

    public void m2() {

        List<Cook> cook = cookRepository.findByKitchenName("KITCHEN_b9044429-7e1d-4bc2-a69e-358cf23fa237", Role.COOK);
        log.info("===>{} - {}", cook.size(), cook);
    }


    @Test
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