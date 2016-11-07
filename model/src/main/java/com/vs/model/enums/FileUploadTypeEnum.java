package com.vs.model.enums;

import com.vs.model.props.ReadYML;

import java.io.File;

/**
 * Created by GeetaKrishna on 10/1/2016.
 */
public enum FileUploadTypeEnum {
    PROFILE_PICTURE("profilePic"), MENU_ITEM_PICTURE("menuItemPic");


    String path;

    FileUploadTypeEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getCompletePath(ReadYML readYML, String id, String fileName) {
        return readYML.getBaseFileUploadLocation()+File.separator + path+ File.separator + id+ File.separator + fileName;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
