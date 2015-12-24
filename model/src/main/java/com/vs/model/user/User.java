package com.vs.model.user;

import com.vs.model.enums.Role;
import com.vs.model.user.address.Address;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;

/**
 * Created by GeetaKrishna on 12/21/2015.
 */
@Slf4j
@Data
public abstract class User {

    static final int MAX_LENGTH_KITCHEN_NAME = 120;

    @Id
    private String userName;
    private Role role = Role.DEFAULT;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private Address personalAddress;
    private Boolean enableTextMessaging = false;

    public void setRole(Role role){
        this.role = role;
    }
}

