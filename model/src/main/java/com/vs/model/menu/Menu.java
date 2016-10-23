package com.vs.model.menu;

import com.vs.model.enums.MenuStatus;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Slf4j
@Data
@Document
public class Menu {

    private String userName;
    private String name;
    private MenuStatus status = MenuStatus.ACTIVE;
    private List<Item> items;
    private Date startDate;
    private Date endDate;
    @Id
    private String menuId;

    public String getMenuId(){
       return "MENU_"+userName+"_"+startDate+"_"+endDate;
    }

}

