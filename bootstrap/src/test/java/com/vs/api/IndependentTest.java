package com.vs.api;

import com.mongodb.MongoClient;
import com.vs.model.menu.Menu;
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

    @Test
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