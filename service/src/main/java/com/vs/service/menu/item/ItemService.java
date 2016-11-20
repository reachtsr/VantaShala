package com.vs.service.menu.item;

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
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gopi on 11/19/16.
 */
@Service
public class ItemService implements IItemservice {

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
    public void updateUserMenuItemStatus(String menuId, String itemId, ItemStatus status) {
        itemOperations.updateUserMenuItemStatus(menuId, itemId, status);
    }

    @Override
    public Item getMenuItem(String menuId, String itemId) {
        List<Item> items = repository.findByMenuIdAndItems_Id(menuId, itemId).getItems();
        return items.stream().filter(item -> item.getId().equals(itemId)).findFirst().get();
    }

    @Override
    public void addMenuItem(String menuId, Item item) throws Exception {
        Menu menu = menuService.getMenuById(menuId);
        String itemName = item.getName();
        Item nItem = menu.getItems().stream().filter(item1 -> item1.getName().equalsIgnoreCase(itemName)).findFirst().get();
        if (nItem == null) {
            itemOperations.addNewItemToMenu(menuId, item);
        } else {
            throw new Exception("Item with same name already available");
        }

    }

    @Override
    public void updateMenuItem(String menuId, Item item) throws  Exception{
        Item nItem = getMenuItem(menuId, item.getId());
        if(nItem.getStatus() == ItemStatus.ACTIVE) {
            itemOperations.updateExistingItem(
                    menuId, item
            );
        }else {
            throw  new Exception("ITEM IS NOT ACTIVE TO UPDATE");
        }
    }

    @Override
    public void deleteMenuItem(String menuId, String itemId) throws Exception {
        Item nItem = getMenuItem(menuId, itemId);
        if(nItem.getStatus() == ItemStatus.ACTIVE) {
            itemOperations.deleteExistingItem(
                    menuId, itemId
            );
        }else {
            throw  new Exception("ITEM IS NOT ACTIVE TO DELETE");
        }
    }


    @Override
    public String saveFile(String menuId, String itemId, SaveFileModel saveFileModel) {

        String id = menuId+"_"+itemId;
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
