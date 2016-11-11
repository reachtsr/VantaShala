package com.vs.model;

import jersey.repackaged.com.google.common.base.Preconditions;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gopi on 11/6/16.
 */
@Data
@Slf4j
public class AddNewFiledsToCollection {
    String id;

    Map<String, String> keyValues = new HashMap<>();
    String collectionType;

    public void validate(){
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(collectionType);
        Preconditions.checkArgument((keyValues.size() != 0), "No Fields found");
    }

}
