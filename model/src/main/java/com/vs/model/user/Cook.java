package com.vs.model.user;

import com.vs.model.enums.Role;
import com.vs.model.user.address.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
@Data
@Document(collection="user")
public final class Cook extends User {

    public Cook() {
        super.setRole(Role.COOK);
    }

    @Indexed(unique = true)
    private String kitchenName;
    private String[] subscribers;

    private Address businessAddress;
    private String businessPhone;

}
