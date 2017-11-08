package com.vs.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vs.model.enums.OrderArchivedReason;
import com.vs.model.enums.OrderStatus;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
@Slf4j
@Data
@CompoundIndexes({
        @CompoundIndex(name = "orderedBy_cookUserName_idx", def = "{'orderedBy': 1, 'cookUserName': -1}")
})
public class Order {

    @Indexed
    private String orderedBy;
    private List<CookMenuItem> cookMenuItems;
    private Date orderedDate = Calendar.getInstance().getTime();

    private double totalPrice;
    @Indexed
    private OrderStatus orderStatus = OrderStatus.PLACED;

    @Id
    private String id;

}
