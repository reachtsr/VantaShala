package com.vs.model.menu;

import com.vs.model.enums.Measurment;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
public class Item {

    @Id
    private String id;
    private String name;
    private Measurment measurment;
    private String quatity;
    private String price;
    private Date[] publishedDate;

}
