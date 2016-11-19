package com.vs.api.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Created by GeetaKrishna on 9/29/2016.
 */

@Data
@Slf4j
public class ConstantsGenerator {

    public enum TYPE {
        COOK, CUSTOMER, KITCHEN, ITEM, COOK_EMAIL, CUSTOMER_EMAIL, ORDER, DEFAULT
    }

    private static List<String> COOK = new ArrayList<>();
    private static List<String> CUSTOMER = new ArrayList<>();
    private static List<String> KITCHEN = new ArrayList<>();
    private static List<String> ITEM = new ArrayList<>();

    private static List<String> COOK_EMAIL = new ArrayList<>();
    private static List<String> CUSTOMER_EMAIL = new ArrayList<>();

    private static String kitchen_name;
    private static String cook_username;
    private static String cook_email;
    private static String customer_username;
    private static String customer_email;
    private static Random randomGenerator;

    static {

        cook_username = ConstantsGenerator.generateId(ConstantsGenerator.TYPE.COOK);
        kitchen_name = ConstantsGenerator.generateId(ConstantsGenerator.TYPE.KITCHEN);
        customer_username = ConstantsGenerator.generateId(ConstantsGenerator.TYPE.CUSTOMER);
        cook_email = ConstantsGenerator.generateId(ConstantsGenerator.TYPE.COOK_EMAIL);
        customer_email = ConstantsGenerator.generateId(ConstantsGenerator.TYPE.CUSTOMER_EMAIL);

        log.info("{}", customer_email);
        randomGenerator = new Random();
    }

    public static String anyItem(List list)
    {
        int index = randomGenerator.nextInt(list.size());
        if(index == list.size())
        {
            index = index -1;
        }
        return CUSTOMER.get(index);
    }

    public static String generateId(TYPE type) {

        String id = UUID.randomUUID().toString();
        switch (type) {
            case COOK: {
                COOK.add(id);
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
                id = id + "@cook.com";
                COOK_EMAIL.add(id);
                break;
            }
            case CUSTOMER_EMAIL: {
                id = id + "@customer.com";
                CUSTOMER_EMAIL.add(id);
                break;
            }
        }

        return id;
    }

    public static String retriveRandomIdFromGeneratedList(TYPE type) throws Exception {
        String id = "";
        switch (type) {
            case COOK: {
                id = anyItem(COOK);
                break;
            }
            case CUSTOMER: {
                id = anyItem(CUSTOMER);
                break;
            }
            case KITCHEN: {
                id = anyItem(KITCHEN);
                break;
            }
            case ITEM: {
                id = anyItem(ITEM);
                break;
            }
            case COOK_EMAIL: {
                id = anyItem(COOK_EMAIL);
                break;
            }
            case CUSTOMER_EMAIL: {
                id = anyItem(CUSTOMER_EMAIL);
                break;
            }
        }
        if(id.trim().equals("")){
            throw new Exception("Error Retrieving Random Id");
        }
        return id;
    }

    public static List getList(TYPE type) {
        String id = "";
        switch (type) {
            case COOK: {
                return COOK;
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

}
