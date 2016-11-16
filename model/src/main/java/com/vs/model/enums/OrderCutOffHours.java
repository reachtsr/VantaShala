package com.vs.model.enums;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
public enum OrderCutOffHours {
    TWENTY_FOUR(24), FORTY_EIGHT(48);

    private final int value;

    OrderCutOffHours(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}
