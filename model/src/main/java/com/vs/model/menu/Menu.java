package com.vs.model.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vs.model.enums.ItemStatus;
import com.vs.model.enums.OrderCutOffHours;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Slf4j
@Data
@Document
public class Menu {

    private String userName;
    private String name;
    private List<Item> items;
    private Date endDate;
    private OrderCutOffHours cutOffHours = OrderCutOffHours.TWENTY_FOUR;

    @Id
    private String menuId;

    @JsonIgnore
    private Date createdDate = Calendar.getInstance().getTime();

    public List<Item> getItems(ItemStatus status){

        if(items != null ) {
            return items.stream().filter(e -> e.getStatus() == status).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}

