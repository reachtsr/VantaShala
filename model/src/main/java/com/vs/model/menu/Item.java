package com.vs.model.menu;

import com.vs.model.enums.ItemStatus;
import com.vs.model.enums.Measurement;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Data
@Slf4j
@Document
public class Item {

    @Id
    private ObjectId id;
    private String name;
    private Measurement measurement;
    private String quantity;
    private Double price;
    private Date createdDate;
    private String description;
    private ItemStatus status = ItemStatus.READY_FOR_ORDER;
    private String picture;

}
