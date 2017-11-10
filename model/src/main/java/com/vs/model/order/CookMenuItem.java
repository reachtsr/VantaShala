package com.vs.model.order;

import com.vs.model.menu.Item;
import com.vs.model.user.Cook;
import com.vs.model.user.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by gopi on 10/24/16.
 */
@Data
@Slf4j
@Document
public class CookMenuItem {

    @Indexed
    String cookUserName;
    @Indexed
    ObjectId menuId;
    @Indexed
    ObjectId itemId;
    Integer orderQuantity;
    Double totalQuantityPrice;
    Item itemDetails;
    Cook cookDetails;
}