package com.vs.common.constants;

/**
 * Created by GeetaKrishna on 12/26/2015.
 */
public enum RepositoryConstantName {
    // These constacts exactly matches with yaml file keys. Don't change the values;
    ROLE_COLLECTION_NAME("roleCollectionName"),
    MENU_ITEM_STATUS_COLLECTION_NAME("menuItemStatusCollectionName"),
    ORDER_STATUS_COLLECTION_NAME("orderStatusCollectionName"),
    EMAIL_STATUS_COLLECTION_NAME("emailStatusCollectionName");

    String name;
    RepositoryConstantName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
