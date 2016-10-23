package com.vs.service.menu;

import com.vs.model.enums.MenuStatus;
import com.vs.model.menu.Menu;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
public interface IMenuService {

    public void createUserMenu(Menu menu);
    public void updateUserMenu(Menu menu);
    public void updateUserMenuStatus(String id, MenuStatus status);
    public void deleteUserMenu(String userName, String menuId) throws Exception;
    public List<Menu> getUserMenus(String userName);
    public List<Menu> getUserMenuByName(String userName, String menuName);
    public List<Menu> getUserMenuByNameOrId(String userName, String menuNameOrId);
    public Menu getUserMenuById(String userName, String menuId);

    public boolean menuExists(String menuId);

    public List<Menu> getMenus();
    public List<Menu> getMenuByName(String menuName);
    public Menu getMenuById(String menuName);

}
