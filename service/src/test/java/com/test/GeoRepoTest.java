package com.test;

import com.mongodb.MongoClient;
import com.vs.model.enums.Country;
import com.vs.model.geo.ZipData;
import com.vs.repository.CookRepository;
import com.vs.repository.MenuRepository;
import com.vs.repository.OrderRepository;
import com.vs.repository.USZipCodesRepository;
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

/**
 * Created by GeetaKrishna on 13-Dec-17.
 **/
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringMongoConfiguration.class})
@Slf4j

public class GeoRepoTest {

    @Autowired
    private USZipCodesRepository usZipCodesRepository;

    @Autowired
    private MongoTemplate template;

    @Autowired
    GeoSpatial geoSpatial;

    @Test
    public void m12() {

        ZipData data = usZipCodesRepository.findBy_id("75024");
        log.info("{}", data);
        geoSpatial.getCooksNearBy(Country.USA, "75024", 10);
        log.info("{}", data);
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