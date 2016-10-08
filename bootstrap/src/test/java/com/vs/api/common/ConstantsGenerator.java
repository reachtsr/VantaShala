package com.vs.api.common;

import java.util.UUID;

/**
 * Created by GeetaKrishna on 9/29/2016.
 */
public class ConstantsGenerator {

    public static final String COOK_ID = "COOK_" + UUID.randomUUID().toString();
    public static final String CUSTOMER_ID = "CUSTOMER_" + UUID.randomUUID().toString();
    public static final String KITCHEN_ID = "KITCHEN_" + UUID.randomUUID().toString();

    public static String COOK_EMAIL = UUID.randomUUID().toString() + "_cook@cook.com";
    public static String CUSTOMER_EMAIL = UUID.randomUUID().toString() + "_customer@customer.com";

}
