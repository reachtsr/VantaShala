package com.vs.model.props;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
@Slf4j
@Component
@Data
public class ReadYML {

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private RepoProperties repoProperties;

    @Value("${vs.uploads.location.base}")
    private String baseFileUploadLocation;

//    @Autowired
//    private FileUploadProperties fileUploadProperties;

    public HashMap<String, String> getEmail() {
        return emailProperties.getEmail();
    }

    public HashMap<String, String> getRepos() {
        return repoProperties.getRepos();
    }

//    public String getFileUploadProperties(FileUploadTypeEnum fileUploadTypeEnum, String userName) {
//        return fileUploadProperties.getPath(fileUploadTypeEnum, userName);
//    }

    @PostConstruct
    public void init() {
        log.info("{} object initialized.", ReadYML.class.getName());
        log.debug("Email Properties: {}", emailProperties);
        log.debug("Repo Properties: {}", repoProperties);
        log.debug("File Upload Base Location : {}", baseFileUploadLocation);
    }

}

@Component
@ConfigurationProperties(prefix = "vs")
class EmailProperties {
    private HashMap<String, String> email;

    public HashMap<String, String> getEmail() {
        return email;
    }

    public void setEmail(HashMap<String, String> email) {
        this.email = email;
    }
}

@Component
@ConfigurationProperties(prefix = "vs.mongo")

class RepoProperties {
    private HashMap<String, String> repos;

    public HashMap<String, String> getRepos() {
        return repos;
    }

    public void setRepos(HashMap<String, String> repos) {
        this.repos = repos;
    }
}
//
//@Component
//@ConfigurationProperties(prefix = "vs.uploads")
//@Data
//class FileUploadProperties {
//
//    private String baseLocation;
//    private String profileLocation;
//    private String menuItemLocation;
//    private HashMap<String, String> location;
//
//    @PostConstruct
//    public void init() {
//        baseLocation = location.get("base");
//        profileLocation = location.get("profilePic");
//        menuItemLocation = location.get("menuItemPic");
//    }
//
//    public String getPath(FileUploadTypeEnum fileUploadTypeEnum, String userName) {
//        switch (fileUploadTypeEnum) {
//            case PROFILE_PICTURE:
//                return buildPath(profileLocation, userName);
//            case MENU_ITEM_PICTURE:
//                return buildPath(menuItemLocation, userName);
//        }
//        if (fileUploadTypeEnum == FileUploadTypeEnum.PROFILE_PICTURE) {
//            return buildPath(profileLocation, userName);
//        }
//
//        return "/fileUpload";
//    }
//
//    private String buildPath(String path, String userName) {
//        return baseLocation + userName+ ""+path;
//    }
//}