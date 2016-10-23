package com.vs.model.order;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
@Slf4j
@Data
public class Order {
    private String orderedBy;
    private String[] ordersTo;
    private String[] menuIds;
    private String[] itemIds;
    private String[] orderedDate;
    private double total;

    @Id
    private String id;

}
