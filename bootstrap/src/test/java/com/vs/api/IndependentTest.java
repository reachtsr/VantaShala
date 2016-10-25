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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

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



    public void m3() {

        UUID id = UUID.fromString("7f400e4d-d5d2-16b9-d7a3-50a624e0e889");
        Item item = new Item();
        item.setStatus(ItemStatus.LOCKED);

        Query query = Query.query(Criteria.where("_id").is(id));

        Update update = new Update();
        update.addToSet("items", item);

        this.template.findAndModify(query, update, Menu.class);

    }

    @Test
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