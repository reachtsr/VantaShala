package com.vs.model.menu;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Slf4j
@Data
public class Menu {

    private String userName;
    private String menuName;
    private Item[] items;
    private Date startDate;
    private Date endDate;

    @Id
    public String getMenuId(){
       return "MENU_"+userName+"_"+startDate+"_"+endDate;
    }

}

