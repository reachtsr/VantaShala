package com.vs.service.menu;

import com.vs.model.menu.Menu;
import com.vs.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Service
public class MenuService implements IMenuService {

    @Autowired
    MenuRepository repository;

    @Override
    public void createUserMenu(Menu menu) {
        repository.save(menu);
    }

    @Override
    public void updateUserMenu(Menu menu) {
        repository.save(menu);
    }

    @Override
    public void deleteUserMenu(String userName, String menuId) {
        repository.delete(menuId);
    }

    @Override
    public List<Menu> getUserMenus(String userName) {
        return repository.findByUserName(userName);
    }

    @Override
    public List<Menu> getUserMenuByName(String userName, String menuName) {
        List<Menu> menus = repository.findByUserNameAndName(userName,menuName);
        return menus;
    }

    @Override
    public List<Menu> getUserMenuByNameOrId(String userName, String menuNameOrId) {
       return repository.findByUserNameAndNameOrMenuId(userName, menuNameOrId, menuNameOrId);
    }

    @Override
    public Menu getUserMenuById(String userName, String menuId) {
       return repository.findByUserNameAndMenuId(userName, menuId);
    }

    @Override
    public boolean menuExists(String menuId) {
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
    public Menu getMenuById(String menuId) {
        return repository.findByMenuId(menuId);
    }
}
