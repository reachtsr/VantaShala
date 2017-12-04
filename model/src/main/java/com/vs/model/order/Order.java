package com.vs.model.order;

import com.vs.model.enums.OrderStatusEnum;
import lombok.Data;
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
    private OrderStatusEnum orderStatus = OrderStatusEnum.PLACED;

    @Id
    private String id;

}
