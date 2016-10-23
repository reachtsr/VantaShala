package com.vs.model.menu;

import com.vs.model.enums.Measurment;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Data
@Slf4j
public class Item {

    @Id
    private UUID id;
    private String name;
    private Measurment measurment;
    private String quatity;
    private Double price;
    private Date[] publishedDate;
    private String description;

}
