package com.vs.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vs.model.enums.OrderArchivedReason;
import com.vs.model.enums.OrderStatus;
import lombok.Data;
import lombok.Getter;
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
    private List<UserMenuItem> userMenuItems;
    private Date orderedDate;

    @Getter private double totalPrice;

    private OrderStatus orderStatus = OrderStatus.PLACED;

    @Id
    private String id;

    @JsonIgnore
    private OrderArchivedReason archivedReason;

}
