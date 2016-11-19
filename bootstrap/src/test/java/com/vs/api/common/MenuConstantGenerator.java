package com.vs.api.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Created by GeetaKrishna on 9/29/2016.
 */

@Data
@Slf4j
public class MenuConstantGenerator extends  ConstantsGenerator{

    private static List<String> ITEM = new ArrayList<>();
    private static NavigableMap<String, List<String>> MENU_TO_ITEM_MAP = new TreeMap<>();


    public static String generateMenuItemId(String menuId) throws Exception {
        String id = generateId(TYPE.ITEM);

        if(!MENU_TO_ITEM_MAP.containsKey(menuId)) {
            throw  new Exception("In Valid Menu at Test");
        }

        if (MENU_TO_ITEM_MAP.containsKey(menuId)) {
            if(MENU_TO_ITEM_MAP.get(menuId).size() == 5) {
                throw new Exception("Somethis wrrong");
            }
            MENU_TO_ITEM_MAP.get(menuId).add(id.toString());
        } else {
            List<String> list = new ArrayList<>();
            list.add(id.toString());
            MENU_TO_ITEM_MAP.put(menuId, list);
        }
        log.info("{}", MENU_TO_ITEM_MAP);
        return id;
    }

    public static String getMenuItemId(String menuId) {
        return MENU_TO_ITEM_MAP.get(menuId).get(0);
    }


    public static String getMenu_id() {

        return MENU_TO_ITEM_MAP.lastEntry().getKey();
    }
    public static String createMenuId() {

        String menuId = generateId(TYPE.DEFAULT);
        if (!MENU_TO_ITEM_MAP.containsKey(menuId)) {
                List<String> list = new ArrayList<>();
                MENU_TO_ITEM_MAP.put(menuId, list);
            }
            return menuId;
    }

    public static String retriveMenuIdFromGeneratedList() {

        return MENU_TO_ITEM_MAP.lastEntry().getKey();
    }

    public static void deleteMenu_id(String menu_id) {
        MENU_TO_ITEM_MAP.remove(menu_id);
    }


}
