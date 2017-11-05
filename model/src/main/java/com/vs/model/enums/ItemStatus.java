package com.vs.model.enums;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
public enum ItemStatus {

    READY_FOR_ORDER, ORDER_IN_PLACE, ORDER_LIMIT_REACHED

    // ORDER_LIMIT_REACHED Orders OverFilled - can't take an order
    // READY_FOR_ORDER ready to place an order
    // ORDER_IN_PLACE can't update menu. orders in place.

}
