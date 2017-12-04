package com.vs.service.menu;

import com.vs.model.enums.ItemStatusEnum;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        if(!Objects.isNull(menu.getItems())){
            for (Item item : menu.getItems()) {
                item.setId(new ObjectId());
            }
        }
        repository.insert(menu);
    }

    @Override
    public void updateUserMenu(String userName, Menu menu) {

        menu.setUserName(userName);
        boolean status = menuExists(menu.getId());
        Preconditions.checkArgument(status, "Sorry! Unable to find the menu :" + menu.getId());

        Menu nMenu = repository.findById(menu.getId());
        Preconditions.checkState(nMenu.getUserName().equals(userName), "Operation Not Allowed");

        Preconditions.checkState(
                (nMenu.getItems(ItemStatusEnum.ORDER_LIMIT_REACHED).size() == 0 || menu.getItems(ItemStatusEnum.ORDER_IN_PLACE).size() == 0),
                "Update NOT ALLOWED. USERS ALREADY PLACED ORDERS. ");

        repository.save(menu);
    }

    @Override
    public void deleteUserMenu(String userName, ObjectId menuId) throws Exception {

        Menu menu = repository.findById(menuId);
        Preconditions.checkNotNull(menu, "Menu not found:" + menuId);

        List<Item> items = menu.getItems(ItemStatusEnum.ORDER_IN_PLACE);
        Preconditions.checkState((items.size() == 0), "DELETE NOT ALLOWED. USERS ALREADY PLACED ORDERS.");

        items = menu.getItems(ItemStatusEnum.ORDER_LIMIT_REACHED);
        Preconditions.checkState((items.size() == 0), "DELETE NOT ALLOWED. USERS ALREADY PLACED ORDERS.");

        repository.delete(menuId);
    }

    @Override
    public List<Menu> getUserMenus(String userName) {
        return repository.findByUserName(userName);
    }

    @Override
    public List<Menu> getUserMenuByName(String userName, String menuName) {
        List<Menu> list = new ArrayList<>();
        list.add(repository.findByUserNameAndName(userName, menuName));
        return list;
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
