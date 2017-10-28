package com.vs.service.menu;

import com.vs.model.enums.ItemStatus;
import com.vs.model.menu.Item;
import com.vs.model.menu.Menu;
import com.vs.repository.DBOperations;
import com.vs.repository.MenuRepository;
import jersey.repackaged.com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Service
@Slf4j
public class MenuService implements IMenuService {

    @Autowired
    MenuRepository repository;

    @Autowired
    MongoTemplate template;

    @Autowired
    DBOperations dbOperations;

    @Override
    public void createUserMenu(String userName, Menu menu) {

        menu.setUserName(userName);
        for (Item item : menu.getItems()) {
            item.setId(new ObjectId());
        }

        repository.insert(menu);
    }

    @Override
    public void updateUserMenu(Menu menu) {

        boolean status = menuExists(menu.getId());
        log.info("id: {} - {}", menu.getId(), status);
        Preconditions.checkArgument(status, "Menu doesn't exists :" + menu.getId());

        Menu nMenu = repository.findById(menu.getId());

        List<Item> items = nMenu.getItems(ItemStatus.LOCKED);
        Preconditions.checkState((items.size() == 0), "Update NOT ALLOWED. USERS ALREADY PLACED ORDERS. There are Locked Items in menu. ");

        items = menu.getItems(ItemStatus.HOLD);
        Preconditions.checkState((items.size() == 0), "Update NOT ALLOWED. USERS ALREADY PLACED ORDERS. There are Hold Items in menu. ");

        repository.save(menu);
    }

    @Override
    public void deleteUserMenu(String userName, ObjectId menuId) throws Exception {

        Menu menu = repository.findById(menuId);
        Preconditions.checkNotNull(menu, "Menu not found:" + menuId);

        List<Item> items = menu.getItems(ItemStatus.LOCKED);
        Preconditions.checkState((items.size() == 0), "DELETE NOT ALLOWED. USERS ALREADY PLACED ORDERS. There are Locked Items in menu. ");

        items = menu.getItems(ItemStatus.HOLD);
        Preconditions.checkState((items.size() == 0), "DELETE NOT ALLOWED. USERS ALREADY PLACED ORDERS. There are Hold Items in menu. ");

        repository.delete(menuId);
    }

    @Override
    public List<Menu> getUserMenus(String userName) {
        return repository.findByUserName(userName);
    }

    @Override
    public List<Menu> getUserMenuByName(String userName, String menuName) {
        List<Menu> menus = repository.findByUserNameAndName(userName, menuName);
        return menus;
    }

    @Override
    public Menu getUserMenuById(String userName, ObjectId id) {
        return repository.findByUserNameAndId(userName, id);
    }

    @Override
    public boolean menuExists(ObjectId menuId) {
        return repository.exists(menuId);
    }

    @Override
    public List<Menu> getMenus() {
        return repository.findAll();
    }

    @Override
    public List<Menu> getMenuByName(String menuName) {
        return repository.findByName(menuName);
    }


    @Override
    public Menu getMenuById(ObjectId menuId) {
        return repository.findById(menuId);
    }


}
