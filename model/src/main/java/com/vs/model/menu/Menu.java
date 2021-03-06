package com.vs.model.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vs.model.enums.ItemStatusEnum;
import com.vs.model.enums.OrderCutOffHours;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Transient
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("0yy-MM-dd HH:mm:ss");

    @JsonIgnore
    private String formattedCreatedDate;
    @JsonIgnore
    private String formattedEndDate;

    Menu() {
        formattedCreatedDate = dateFormat.format(createdDate);
    }
    public List<Item> getItems(ItemStatusEnum status){

        if(items != null ) {
            return items.stream().filter(e -> e.getStatus() == status).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    public Date getCreatedDate() {

        try {
            createdDate = dateFormat.parse(formattedCreatedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return createdDate;
    }

    public Date getEndDate() {

        try {
            endDate = dateFormat.parse(formattedEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
        setFormattedEndDate( dateFormat.format(endDate) );
    }

    public String getFormattedEndDate() {

        return formattedEndDate;
    }

    public void setFormattedEndDate(String formattedEndDate) {

        this.formattedEndDate = formattedEndDate;
    }

}

