package com.vs.api.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;

import java.util.*;

/**
 * Created by GeetaKrishna on 9/29/2016.
 */

@Data
@Slf4j
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


    private static String kitchen_name;
    private static String cook_username;
    private static String cook_email;
    private static String customer_username;
    private static String customer_email;
    private static String menu_id;


    static {

        cook_username = ConstantsGenerator.generateId(ConstantsGenerator.TYPE.COOK);
        kitchen_name = ConstantsGenerator.generateId(ConstantsGenerator.TYPE.KITCHEN);
        customer_username = ConstantsGenerator.generateId(ConstantsGenerator.TYPE.CUSTOMER);
        cook_email = ConstantsGenerator.generateId(ConstantsGenerator.TYPE.COOK_EMAIL);
        customer_email = ConstantsGenerator.generateId(ConstantsGenerator.TYPE.CUSTOMER_EMAIL);
        menu_id = ConstantsGenerator.generateId(TYPE.MENU);

        log.info("{}", customer_email);
    }


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

    public static String getMenuItemId(String menuId) {
            return MENU_TO_ITEM_MAP.get(menuId).get(0);
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
                id=id+"@cook.com";
                COOK_EMAIL.add(id);
                break;
            }
            case CUSTOMER_EMAIL: {
                id=id+"@customer.com";
                CUSTOMER_EMAIL.add(id);
                break;
            }
        }

        return id;
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

    public static List getList(TYPE type) {
        String id = "";
        switch (type) {
            case COOK: {
                return COOK;
            }
            case MENU: {
                return MENU;
            }
            case CUSTOMER: {
                return CUSTOMER;
            }
            case KITCHEN: {
                return KITCHEN;
            }
            case ITEM: {
                return ITEM;
            }
            case COOK_EMAIL: {
                return COOK_EMAIL;
            }
            case CUSTOMER_EMAIL: {
                return CUSTOMER_EMAIL;
            }
        }
        return null;
    }

    public static List<String> getCOOK() {
        return COOK;
    }


    public static List<String> getMENU() {
        return MENU;
    }

    public static List<String> getCUSTOMER() {
        return CUSTOMER;
    }

    public static List<String> getKITCHEN() {
        return KITCHEN;
    }

    public static List<String> getITEM() {
        return ITEM;
    }

    public static List<String> getCookEmail() {
        return COOK_EMAIL;
    }

    public static List<String> getCustomerEmail() {
        return CUSTOMER_EMAIL;
    }

    public static Map<String, List<String>> getMenuToItemMap() {
        return MENU_TO_ITEM_MAP;
    }

    public static String getKitchen_name() {
        return kitchen_name;
    }

    public static String getCook_username() {
        return cook_username;
    }

    public static String getCook_email() {
        return cook_email;
    }

    public static String getCustomer_username() {
        return customer_username;
    }

    public static String getCustomer_email() {
        return customer_email;
    }

    public static String getMenu_id() {
        return menu_id;
    }



}
