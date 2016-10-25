package com.vs.model.order;

import com.vs.model.enums.OrderStatus;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
@Slf4j
@Data
public class Order {
    private String orderedBy;
    private String[] ordersTo;
    private List<MenuToItem> itemToMenus;
    private Date orderedDate;
    private double total;

    private OrderStatus orderStatus;

    @Id
    private String id;

}
