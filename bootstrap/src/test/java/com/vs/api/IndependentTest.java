package com.vs.api;

import com.mongodb.MongoClient;
import com.vs.model.enums.Role;
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
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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

    @Test
    public void m2() {

        List<Cook> cook = cookRepository.findByKitchenName("KITCHEN_b9044429-7e1d-4bc2-a69e-358cf23fa237", Role.COOK);
        log.info("===>{} - {}", cook.size(), cook);
    }

    public void m1() {

        boolean exists = menuRepository.exists("MENU_806a0d55-d1d7-4f89-ace3-d7a9138e7498");
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