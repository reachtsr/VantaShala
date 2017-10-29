package com.vs.service.menu.item;

import com.vs.model.SaveFileModel;
import com.vs.model.enums.ItemStatus;
import com.vs.model.menu.Item;
import org.bson.types.ObjectId;

/**
 * Created by gopi on 11/19/16.
 */
public interface IItemservice {
    public void updateUserMenuItemStatus(ObjectId menuId, ObjectId itemId, ItemStatus status);

    public Item getMenuItem(ObjectId menuId, ObjectId itemId) throws Exception;

    public void addMenuItem(ObjectId menuId, Item item) throws Exception;

    public void updateMenuItem(ObjectId menuId, Item item) throws Exception;

    public void deleteMenuItem(ObjectId menuId, ObjectId itemId) throws Exception;

    public String saveFile(ObjectId menuId, ObjectId itemId, SaveFileModel saveFile);
}
