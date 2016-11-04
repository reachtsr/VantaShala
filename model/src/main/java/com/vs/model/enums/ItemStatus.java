package com.vs.model.enums;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
public enum ItemStatus {

    HOLD, ACTIVE, LOCKED, EXPIRED;

    // HOLD During update - can't take an order
    // ACTIVE ready to place an order
    // LOCKED can't update menu. orders in place.
    // EXPIRED: Outdated menu
}
