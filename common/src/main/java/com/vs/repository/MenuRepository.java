package com.vs.repository;

import com.vs.model.menu.Item;
import com.vs.model.menu.Menu;
import com.vs.model.order.Order;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
public interface MenuRepository extends MongoRepository<Menu, ObjectId> {

    public List<Menu> findByUserNameAndName(String userName, String name);
    public List<Menu> findByUserName(String userName);
    public Menu findByUserNameAndId(String userName, ObjectId id);

    public Menu findById(ObjectId id);
    public List<Menu> findByName(String menuName);
    public boolean exists(ObjectId id);
    public Menu findOne(ObjectId id);
    public List<Menu> findAll();
    public long count();
    public long countByUserName(String userName);

    //@Query(value = "{'id' : ?0, 'items.id': ?1}", fields = "{'items.id':1}")
    public Menu findByIdAndItems_Id(ObjectId id, ObjectId itemId);

}
