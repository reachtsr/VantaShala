package com.test;

import com.mongodb.MongoClient;
import com.vs.model.enums.Country;
import com.vs.model.user.Cook;
import com.vs.service.geo.GeoSpatial;
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
 * Created by GeetaKrishna on 13-Dec-17.
 **/
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringMongoConfiguration.class})
@Slf4j

public class GeoRepoTest {

    @Autowired
    private MongoTemplate template;

    @Autowired
    GeoSpatial geoSpatial;

    @Test
    public void m12() {
        List<Cook> cooks = geoSpatial.getCooksNearBy(Country.US, "75034", 10);
        log.info("{}", cooks);
    }

}

@EnableMongoRepositories({"com.vs.repository"})
@ComponentScan(basePackages = "com.vs.service.geo")
class SpringMongoConfiguration {

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(new MongoClient("localhost"), "VantaShala");
    }

}