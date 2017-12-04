package com.vs.service.bootstrap;

import com.mongodb.BasicDBObject;
import com.vs.common.Bootstrap;
import com.vs.common.constants.RepositoryConstantName;
import com.vs.model.enums.*;
import com.vs.model.props.ReadYML;
import com.vs.model.props.RepoProperties;
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

    @Autowired
    RepoProperties repoProperties;

//    Todo Check unsent emails if there are any schedule them to send now and update the status in DB.

    public void initialize(){
        checkAndCreateCollections();
    }

    private void checkAndCreateCollections() {
        String repo = repoProperties.getRepos().values().iterator().next();

        log.info("Checking Collection: {}", repo);
        boolean isCollectionExists = template.collectionExists(repo);

        if(isCollectionExists) {
            log.info("Collection Available");
            // Check Super Admin
        } else {
            log.info("Collections not exists, Creating them now.");
            log.info("Reading Collections");

            repoProperties.getRepos().forEach((k, v) -> template.createCollection(v));

            createRoles();
            createMenuItemStatus();
            createOrderStatus();
            createEmailStatus();
            createUserStatus();
        }
    }

    private void createDBEnums(String name, String[] enums, RepositoryConstantName repositoryName){
        BasicDBObject obj = new BasicDBObject();
        obj.put(name, enums);
        log.info("Enums {}, Repository Name: {}",enums.toString(), repositoryName.getName());
        template.insert(obj, repoProperties.getRepos().get(repositoryName.getName()));
    }

    private void createRoles(){
        String[] enums = Arrays.stream(Role.class.getEnumConstants()).map(Enum::name).toArray(String[]::new);
        createDBEnums("roles", enums, RepositoryConstantName.ENUM_COLLECTION_NAME);
    }
    private void createMenuItemStatus(){
        String[] enums = Arrays.stream(ItemStatusEnum.class.getEnumConstants()).map(Enum::name).toArray(String[]::new);
        createDBEnums("menuItemStatus", enums, RepositoryConstantName.ENUM_COLLECTION_NAME);
    }
    private void createOrderStatus(){
        String[] enums = Arrays.stream(OrderStatusEnum.class.getEnumConstants()).map(Enum::name).toArray(String[]::new);
        createDBEnums("orderStatusEnum", enums, RepositoryConstantName.ENUM_COLLECTION_NAME);
    }
    private void createEmailStatus(){
        String[] enums = Arrays.stream(EmailStatusEnum.class.getEnumConstants()).map(Enum::name).toArray(String[]::new);
        createDBEnums("emailStatus", enums, RepositoryConstantName.ENUM_COLLECTION_NAME);
    }
    private void createUserStatus(){
        String[] enums = Arrays.stream(UserStatusEnum.class.getEnumConstants()).map(Enum::name).toArray(String[]::new);
        createDBEnums("userStatus", enums, RepositoryConstantName.ENUM_COLLECTION_NAME);
    }
}
