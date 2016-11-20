package com.vs.service.menu.item;

import com.vs.model.SaveFileModel;
import com.vs.model.enums.ItemStatus;
import com.vs.model.menu.Item;

/**
 * Created by gopi on 11/19/16.
 */
public interface IItemservice {
    public void updateUserMenuItemStatus(String menuId, String itemId, ItemStatus status);

    public Item getMenuItem(String menuId, String itemId);

    public void addMenuItem(String menuId, Item item) throws Exception;

    public void updateMenuItem(String menuId, Item item) throws Exception;

    public void deleteMenuItem(String menuId, String itemId) throws Exception;

    public String saveFile(String menuId, String itemId, SaveFileModel saveFile);
}
