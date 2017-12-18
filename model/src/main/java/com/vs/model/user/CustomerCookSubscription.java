package com.vs.model.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by gopi on 11/19/2016.
 */
@Component
@Data
public class CustomerCookSubscription {

    /*
    UserId can be cook or customer
     */
    @Id
    String userId;

    /*
    Subscribers can be Cooks or Customers
     */
    List<String> subscribers;

}
