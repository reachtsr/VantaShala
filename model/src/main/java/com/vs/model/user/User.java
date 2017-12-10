package com.vs.model.user;

import com.vs.model.enums.Role;
import com.vs.model.enums.UserStatusEnum;
import com.vs.model.user.address.Address;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

/**
 * Created by GeetaKrishna on 12/21/2015.
 */
@Slf4j
@Data
@Document
public abstract class User {

    static final int MAX_LENGTH_KITCHEN_NAME = 120;

    @Id
    private String userName;
    private Role role = Role.DEFAULT;
    private String firstName;
    private String lastName;
    private double[] location;
    @Indexed(unique = true)
    @NotNull
    private String email;
    private String mobile;
    private Address personalAddress;
    private UserStatusEnum status;
    private Boolean enableTextMessaging = false;

    public void setRole(Role role) {
        this.role = role;
    }

}

