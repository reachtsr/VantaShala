package com.vs.service.bootstrap;

import com.mongodb.BasicDBObject;
import com.vs.common.Bootstrap;
import com.vs.model.enums.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by GeetaKrishna on 12/21/2015.
 */
@Component
@Slf4j
@ConfigurationProperties(prefix = "vs")
public class ServiceBootstrap implements Bootstrap{

    @Autowired
    MongoTemplate template;

    @Value("${vs.mongo.roleCollectionName}")
    private String roleCollectionName;

    @Value("${vs.mongo.userCollectionName}")
    private String userCollectionName;

    public void initialize(){
        log.info("Initializing the Services Service...");
        checkAndCreateCollections();

    }

    private void checkAndCreateCollections() {
        log.info("Checking Collection: {}", userCollectionName);
        boolean isCollectionExists = template.collectionExists(userCollectionName);

        if(isCollectionExists) {
            log.info("Collection Available");
            // Check Super Admin
        } else {
            log.info("Collection not exists, Creating it now");
            template.createCollection(userCollectionName);
            template.createCollection(roleCollectionName);
            createRoles();
        }
    }
    private void createRoles(){
        String[] roles =Arrays.stream(Role.class.getEnumConstants()).map(Enum::name).toArray(String[]::new);
        log.info("Roles {}",roles.toString());
        BasicDBObject roleObj = new BasicDBObject();
        roleObj.put("roles", roles);
        template.insert(roleObj, roleCollectionName);
    }

}
