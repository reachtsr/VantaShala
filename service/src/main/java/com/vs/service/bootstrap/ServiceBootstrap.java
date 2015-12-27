package com.vs.service.bootstrap;

import com.mongodb.BasicDBObject;
import com.vs.common.Bootstrap;
import com.vs.common.constants.EmailConstants;
import com.vs.common.constants.RepositoryConstants;
import com.vs.model.enums.EmailStatus;
import com.vs.model.enums.MenuStatus;
import com.vs.model.enums.OrderStatus;
import com.vs.model.enums.Role;
import com.vs.props.ReadYML;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ReadYML readYML;

//    Todo Check unsent emails if there are any schedule them to send now and update the status in DB.

    public void initialize(){
        checkAndCreateCollections();
    }

    private void checkAndCreateCollections() {
        String repo = readYML.getRepos().values().iterator().next();

        log.info("Checking Collection: {}", repo);
        boolean isCollectionExists = template.collectionExists(repo);

        if(isCollectionExists) {
            log.info("Collection Available");
            // Check Super Admin
        } else {
            log.info("Collections not exists, Creating them now.");
            log.info("Reading Collections");

            readYML.getRepos().forEach((k, v) -> {
                template.createCollection(v);
            });

            createRoles();
            createMenuStatus();
            createOrderStatus();
            createEmailStatus();
        }
    }

    private void createRoles(){
        String[] enums = Arrays.stream(Role.class.getEnumConstants()).map(Enum::name).toArray(String[]::new);
        log.info("Roles {}",enums.toString());
        BasicDBObject roleObj = new BasicDBObject();
        roleObj.put("roles", enums);
        template.insert(roleObj, readYML.getRepos().get(RepositoryConstants.ROLE_COLLECTION_NAME));
    }
    private void createMenuStatus(){
        String[] enums = Arrays.stream(MenuStatus.class.getEnumConstants()).map(Enum::name).toArray(String[]::new);
        log.info("Menu Status {}",enums.toString());
        BasicDBObject roleObj = new BasicDBObject();
        roleObj.put("menuStatus", enums);
        template.insert(roleObj, readYML.getRepos().get(RepositoryConstants.MENU_STATUS_COLLECTION_NAME));
    }
    private void createOrderStatus(){
        String[] enums = Arrays.stream(OrderStatus.class.getEnumConstants()).map(Enum::name).toArray(String[]::new);
        log.info("Order Status {}",enums.toString());
        BasicDBObject roleObj = new BasicDBObject();
        roleObj.put("orderStatus", enums);
        template.insert(roleObj, readYML.getRepos().get(RepositoryConstants.ORDER_STATUS_COLLECTION_NAME));
    }
    private void createEmailStatus(){
        String[] enums = Arrays.stream(EmailStatus.class.getEnumConstants()).map(Enum::name).toArray(String[]::new);
        log.info("Order Status {}",enums.toString());
        BasicDBObject roleObj = new BasicDBObject();
        roleObj.put("emailStatus", enums);
        template.insert(roleObj, readYML.getRepos().get(RepositoryConstants.EMAIL_STATUS_COLLECTION_NAME));
    }

}
