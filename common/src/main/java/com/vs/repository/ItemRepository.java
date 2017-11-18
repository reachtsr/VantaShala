package com.vs.repository;

import com.vs.model.menu.Menu;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
public interface ItemRepository extends MongoRepository<Menu, ObjectId> {

    public Menu findByUserNameAndIdAndItems_Id(String userName, ObjectId menuId, ObjectId itemId);

}
