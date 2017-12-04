package com.vs.model.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vs.model.enums.ItemStatusEnum;
import com.vs.model.enums.OrderCutOffHours;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Slf4j
@Data
@Document
@CompoundIndexes({
        @CompoundIndex(name = "user_menuName_idx", def = "{'userName': 1, 'name': -1}")
})
public class Menu {

    @Id
    private ObjectId id;

    @JsonIgnore
    @Indexed
    private String userName;
    @Indexed
    private String name;
    private List<Item> items;
    private Date endDate;
    private OrderCutOffHours cutOffHours = OrderCutOffHours.TWENTY_FOUR;

    private Date createdDate = Calendar.getInstance().getTime();

    public List<Item> getItems(ItemStatusEnum status){

        if(items != null ) {
            return items.stream().filter(e -> e.getStatus() == status).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}

