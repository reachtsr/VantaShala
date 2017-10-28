package com.vs.model.order;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

/**
 * Created by gopi on 10/24/16.
 */
@Data
@Slf4j
public class CookMenuItem {

    ObjectId cookId;
    ObjectId menuId;
    ObjectId itemId;
}