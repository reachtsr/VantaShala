package com.vs.repository;

import com.vs.model.menu.Menu;
import com.vs.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
public interface MenuRepository extends MongoRepository<Menu, String> {

    public List<Menu> findByUserNameAndName(String userName, String menuName);
    public List<Menu> findByUserName(String userName);
    public Menu findByUserNameAndMenuId(String userName, String menuId);
    public List<Menu> findByUserNameAndNameOrMenuId(String userName, String menuName, String menuId);
    public Menu findByMenuId(String menuId);
    public List<Menu> findByName(String menuName);
    public boolean exists(String menuId);
    public Menu findOne(String menuId);
    public List<Menu> findAll();
    public long count();
    public long countByUserName(String userName);
}
