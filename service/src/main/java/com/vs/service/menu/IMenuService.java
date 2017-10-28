package com.vs.service.menu;

import com.vs.model.menu.Menu;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
public interface IMenuService {

    public void createUserMenu(String userName, Menu menu);

    public void updateUserMenu(String userName, Menu menu);

    public void deleteUserMenu(String userName, ObjectId menuId) throws Exception;

    public List<Menu> getUserMenus(String userName);

    public List<Menu> getUserMenuByName(String userName, String menuName);

    public Menu getUserMenuById(String userName, ObjectId menuId);

    public boolean menuExists(ObjectId menuId);

    public List<Menu> getMenus();

    public List<Menu> getMenuByName(String menuName);

    public Menu getMenuById(ObjectId menuId);


}
