package com.vs.model.menu;

import com.vs.model.enums.ItemStatus;
import com.vs.model.enums.Measurment;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Data
@Slf4j
public class Item {

    @Id
    private String id;
    private String name;
    private Measurment measurment;
    private String quatity;
    private Double price;
    private Date createdDate;
    private String description;
    private ItemStatus status = ItemStatus.ACTIVE;
    private String picture;

}
