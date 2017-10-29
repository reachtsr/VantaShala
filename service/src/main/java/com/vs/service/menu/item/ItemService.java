package com.vs.service.menu.item;

import com.vs.common.errorHandling.CustomReasonPhraseException;
import com.vs.common.errorHandling.ErrorMessage;
import com.vs.common.filters.AppConstants;
import com.vs.model.AddNewFiledsToCollection;
import com.vs.model.SaveFileModel;
import com.vs.model.enums.ItemStatus;
import com.vs.model.menu.Item;
import com.vs.model.menu.Menu;
import com.vs.repository.ItemOperations;
import com.vs.repository.MenuRepository;
import com.vs.service.SaveFile;
import com.vs.service.menu.MenuService;
import jersey.repackaged.com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * Created by gopi on 11/19/16.
 */
@Service
@Slf4j
public class ItemService implements IItemservice {

    @PostConstruct
    public void init() {
        log.info("ItemService Initialized");
    }

    @Autowired
    SaveFile saveFile;

    @Autowired
    MenuRepository repository;

    @Autowired
    MongoTemplate template;

    @Autowired
    ItemOperations itemOperations;

    @Autowired
    MenuService menuService;

    @Override
    public void updateUserMenuItemStatus(ObjectId menuId, ObjectId itemId, ItemStatus status) {
        itemOperations.updateUserMenuItemStatus(menuId, itemId, status);
    }

    @Override
    public Item getMenuItem(ObjectId menuId, ObjectId itemId) throws Exception {

        Menu menu = repository.findByIdAndItems_Id(menuId, itemId);
        if(Objects.isNull(menu)){
            CustomReasonPhraseException exception = new CustomReasonPhraseException(Response.Status.NOT_FOUND, "Check your Ids Provided");
            throw exception;
        }
        List<Item> items = menu.getItems();
        return items.stream().filter(item -> item.getId().equals(itemId)).findAny().orElse(null);
    }

    @Override
    public void addMenuItem(ObjectId menuId, Item item) throws Exception {
        Menu menu = menuService.getMenuById(menuId);

        String itemName = item.getName();
        ObjectId id = new ObjectId();
        item.setId(id);

        if (!Objects.isNull(menu.getItems()) && menu.getItems().size() > 0) {
            Item nItem = menu.getItems().stream().
                    filter(item1 -> itemName.equalsIgnoreCase(item1.getName())).findAny().orElse(null);
            if (nItem == null) {
                itemOperations.addNewItemToExistingItems(menuId, item);
            } else {
                CustomReasonPhraseException exception = new CustomReasonPhraseException(Response.Status.BAD_REQUEST, "Item with same name already exists");
                throw exception;
            }
        } else {
            itemOperations.addNewItemToMenu(menuId, item);
        }
    }

    @Override
    public void updateMenuItem(ObjectId menuId, Item item) throws Exception {
        Item nItem = getMenuItem(menuId, item.getId());
        if (nItem.getStatus() == ItemStatus.ACTIVE) {
            itemOperations.updateExistingItem(
                    menuId, item
            );
        } else {
            CustomReasonPhraseException exception = new CustomReasonPhraseException(Response.Status.BAD_REQUEST, "Not allowed to update, Orders are already in place.");
            throw exception;
        }
    }

    @Override
    public void deleteMenuItem(ObjectId menuId, ObjectId itemId) throws Exception {
        Item nItem = getMenuItem(menuId, itemId);
        if (nItem.getStatus() == ItemStatus.ACTIVE) {
            itemOperations.deleteExistingItem(
                    menuId, itemId
            );
        } else {
            throw new Exception("ITEM IS NOT ACTIVE TO DELETE");
        }
    }


    @Override
    public String saveFile(ObjectId menuId, ObjectId itemId, SaveFileModel saveFileModel) {

        String id = menuId + "_" + itemId;
        String path = saveFile.saveFile(id, saveFileModel);

        Map<String, String> map = new HashMap();
        map.put(AppConstants.MENU_ITEM_PICTURE, path);

        AddNewFiledsToCollection addNewFiledsToCollection = new AddNewFiledsToCollection();
        addNewFiledsToCollection.setId(id);
        addNewFiledsToCollection.setCollectionType(Menu.class.getName());
        addNewFiledsToCollection.setKeyValues(map);

        itemOperations.addPictureTotem(menuId, itemId, addNewFiledsToCollection);


        return path;
    }

}
