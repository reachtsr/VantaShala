package com.vs.common.constants;

/**
 * Created by GeetaKrishna on 12/26/2015.
 */
public enum RepositoryConstantName {
    // These constants must exactly match with yml file keys. Don't change the values;
    ENUM_COLLECTION_NAME("enumCollectionName");

    String name;

    RepositoryConstantName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
