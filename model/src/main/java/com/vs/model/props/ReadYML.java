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

    @Value("${vs.uploads.location.base}")
    private String baseFileUploadLocation;


//    @Autowired
//    private FileUploadProperties fileUploadProperties;

//    public String getFileUploadProperties(FileUploadTypeEnum fileUploadTypeEnum, String userName) {
//        return fileUploadProperties.getPath(fileUploadTypeEnum, userName);
//    }

    @PostConstruct
    public void init() {
        log.info("{} object initialized.", ReadYML.class.getName());
        log.debug("File Upload Base Location : {}", baseFileUploadLocation);
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
//    private HashMap<String, String> loc;
//
//    @PostConstruct
//    public void init() {
//        baseLocation = loc.get("base");
//        profileLocation = loc.get("profilePic");
//        menuItemLocation = loc.get("menuItemPic");
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