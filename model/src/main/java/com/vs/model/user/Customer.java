package com.vs.model.user;

import com.vs.model.enums.Role;
import com.vs.model.user.address.Address;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
@Data
@Document
public final class Customer extends User {

    public Customer(){
        super.setRole(Role.CUSTOMER);
    }

}
