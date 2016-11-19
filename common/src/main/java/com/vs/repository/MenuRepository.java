package com.vs.repository;

import com.vs.model.menu.Item;
import com.vs.model.menu.Menu;
import com.vs.model.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
public interface MenuRepository extends MongoRepository<Menu, String> {

    public List<Menu> findByUserNameAndName(String userName, String name);
    public List<Menu> findByUserName(String userName);
    public Menu findByUserNameAndMenuId(String userName, String menuId);

    public List<Menu> findByUserNameOrNameAndMenuId(String userName, String name, String menuId);
    public Menu findByMenuId(String menuId);
    public List<Menu> findByName(String menuName);
    public boolean exists(String menuId);
    public Menu findOne(String menuId);
    public List<Menu> findAll();
    public long count();
    public long countByUserName(String userName);

    //@Query(value = "{'_id' : ?0, 'items._id': ?1}", fields = "{'items._id':1}")
    public Menu findByMenuIdAndItems_Id(String menuId, String itemId);

}
