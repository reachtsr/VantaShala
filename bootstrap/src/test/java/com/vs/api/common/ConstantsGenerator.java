package com.vs.api.common;

import java.util.*;

/**
 * Created by GeetaKrishna on 9/29/2016.
 */
public class ConstantsGenerator {

    public enum TYPE {
        COOK, MENU, CUSTOMER, KITCHEN, ITEM, COOK_EMAIL, CUSTOMER_EMAIL
    }

    private static List<String> COOK = new ArrayList<>();
    private static List<String> MENU = new ArrayList<>();
    private static List<String> CUSTOMER = new ArrayList<>();
    private static List<String> KITCHEN = new ArrayList<>();
    private static List<String> ITEM = new ArrayList<>();

    private static List<String> COOK_EMAIL = new ArrayList<>();
    private static List<String> CUSTOMER_EMAIL = new ArrayList<>();

    private static Map<String, List<String>> MENU_TO_ITEM_MAP = new HashMap<>();
//
//    public static final String COOK_ID = "COOK_" + UUID.randomUUID().toString();
//    public static final String CUSTOMER_ID = "CUSTOMER_" + UUID.randomUUID().toString();
//    public static final String KITCHEN_ID = "KITCHEN_" + UUID.randomUUID().toString();
//    public static final String MENU_ID = "MENU_" + UUID.randomUUID().toString();
//    public static final List<UUID> itemIds = new ArrayList<>();
//
//    public static String COOK_EMAIL = UUID.randomUUID().toString() + "_cook@cook.com";
//    public static String CUSTOMER_EMAIL = UUID.randomUUID().toString() + "_customer@customer.com";

    public static String generateMenuItemId(String menuId) {
        String id = generateId(TYPE.ITEM);
        if (MENU_TO_ITEM_MAP.containsKey(menuId)) {
            MENU_TO_ITEM_MAP.get(menuId).add(id.toString());
        } else {
            List<String> list = new ArrayList<>();
            list.add(id.toString());
            MENU_TO_ITEM_MAP.put(menuId, list);
        }

        return id;
    }

    public static String generateId(TYPE type) {


        String id = UUID.randomUUID().toString();
        switch (type) {
            case COOK: {
                COOK.add(id);
                break;
            }
            case MENU: {
                MENU.add(id);
                break;
            }
            case CUSTOMER: {
                CUSTOMER.add(id);
                break;
            }
            case KITCHEN: {
                KITCHEN.add(id);
                break;
            }
            case ITEM: {
                ITEM.add(id);
                break;
            }
            case COOK_EMAIL: {
                COOK_EMAIL.add(id.toString()+"@cook.com");
                break;
            }
            case CUSTOMER_EMAIL: {
                CUSTOMER_EMAIL.add(id.toString()+"customer.com");
                break;
            }
        }

        return id.toString();
    }

    public static String retriveRandomIdFromGeneratedList(TYPE type){
        String id = "";
        switch (type) {
            case COOK: {
                id = COOK.get(0).toString();
                break;
            }
            case MENU: {
                id = MENU.get(0).toString();
                break;
            }
            case CUSTOMER: {
                id = CUSTOMER.get(0).toString();
                break;
            }
            case KITCHEN: {
                id = KITCHEN.get(0).toString();
                break;
            }
            case ITEM: {
                id = ITEM.get(0).toString();
                break;
            }
            case COOK_EMAIL: {
                id = COOK_EMAIL.get(0).toString();
                break;
            }
            case CUSTOMER_EMAIL: {
                id = CUSTOMER_EMAIL.get(0).toString();
                break;
            }
        }
        return id;
    }
}
