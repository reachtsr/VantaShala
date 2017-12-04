package com.vs.service.menu.item;

import com.vs.common.errorHandling.CustomReasonPhraseException;
import com.vs.model.SaveFileModel;
import com.vs.model.enums.ItemStatusEnum;
import com.vs.model.menu.Item;
import com.vs.model.menu.Menu;
import com.vs.repository.ItemOperations;
import com.vs.repository.MenuRepository;
import com.vs.service.SaveFile;
import com.vs.service.menu.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

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
    public void updateUserMenuItemStatus(ObjectId menuId, ObjectId itemId, ItemStatusEnum status) throws Exception {
        Item nItem = getMenuItem(menuId, itemId);
        if (nItem.getStatus() != ItemStatusEnum.READY_FOR_ORDER) {
            CustomReasonPhraseException exception = new CustomReasonPhraseException(Response.Status.BAD_REQUEST, "Not allowed to update, Orders are already in place.");
            throw exception;
        }

        itemOperations.updateUserMenuItemStatus(menuId, itemId, status);
    }

    @Override
    public Item getMenuItem(ObjectId menuId, ObjectId itemId) throws Exception {

        Menu menu = repository.findByIdAndItems_Id(menuId, itemId);
        if (Objects.isNull(menu)) {
            CustomReasonPhraseException exception = new CustomReasonPhraseException(Response.Status.NOT_FOUND, "Check your Ids Provided");
            throw exception;
        }
        List<Item> items = menu.getItems();
        return items.stream().filter(item -> item.getId().equals(itemId)).findAny().orElse(null);
    }

    @Override
    public void addMenuItem(ObjectId menuId, List<Item> items) throws Exception {
        Menu menu = menuService.getMenuById(menuId);

        boolean doTransct = true;
        StringBuilder error = new StringBuilder();

        if (!Objects.isNull(menu.getItems()) && menu.getItems().size() > 0) {
            for (Item item : items) {

                String itemName = item.getName();
                Item nItem = menu.getItems().stream().
                        filter(item1 -> itemName.equalsIgnoreCase(item1.getName())).findAny().orElse(null);
                if (nItem != null) {
                    error.append(item.getName()).append(" ");
                    doTransct = false;
                }
            }
            if (doTransct) {
                generateIds(items);
                itemOperations.addNewItemToExistingItems(menuId, items);
            } else {
                error.append("already exists");
                CustomReasonPhraseException exception = new CustomReasonPhraseException(Response.Status.BAD_REQUEST, error.toString());
                throw exception;
            }
        } else {
            generateIds(items);
            itemOperations.addNewItemToMenu(menuId, items);
        }

    }

    private void generateIds(List<Item> items) {
        for (Item item : items) {
            ObjectId id = new ObjectId();
            item.setId(id);
        }
    }

    @Override
    public void updateMenuItem(ObjectId menuId, Item item) throws Exception {
        Item nItem = getMenuItem(menuId, item.getId());
        if (nItem.getStatus() == ItemStatusEnum.READY_FOR_ORDER) {
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
        if (nItem.getStatus() == ItemStatusEnum.READY_FOR_ORDER) {
            itemOperations.deleteExistingItem(
                    menuId, itemId
            );
        } else {
            CustomReasonPhraseException exception = new CustomReasonPhraseException(Response.Status.BAD_REQUEST, "Not allowed to delete, Orders are already in place.");
            throw exception;
        }
    }


    @Override
    public String saveFile(ObjectId menuId, ObjectId itemId, SaveFileModel saveFileModel) {

        String id = menuId + "_" + itemId;
        String path = saveFile.saveFile(id, saveFileModel);
        itemOperations.addPictureTotem(menuId, itemId, path);

        return path;
    }

    @Override
    public List<Item> listMenuItems(ObjectId menuId) throws Exception {
        return null;
    }
}
