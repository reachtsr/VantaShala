package com.vs.model.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by gopi on 11/19/2016.
 */
@Component
@Data
public class CustomerToCookSubscription {

    @Id
    String cook;
    List<String> customers;
}
