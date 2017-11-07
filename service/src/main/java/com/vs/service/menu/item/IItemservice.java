package com.vs.service.menu.item;

import com.vs.model.SaveFileModel;
import com.vs.model.enums.ItemStatus;
import com.vs.model.menu.Item;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by gopi on 11/19/16.
 */
public interface IItemservice {
    void updateUserMenuItemStatus(ObjectId menuId, ObjectId itemId, ItemStatus status) throws Exception;

    List<Item> listMenuItems(ObjectId menuId) throws Exception;

    Item getMenuItem(ObjectId menuId, ObjectId itemId) throws Exception;

    void addMenuItem(ObjectId menuId, List<Item> item) throws Exception;

    void updateMenuItem(ObjectId menuId, Item item) throws Exception;

    void deleteMenuItem(ObjectId menuId, ObjectId itemId) throws Exception;

    String saveFile(ObjectId menuId, ObjectId itemId, SaveFileModel saveFile);
}
