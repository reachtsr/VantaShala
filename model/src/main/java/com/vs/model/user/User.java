package com.vs.model.user;

import com.vs.model.enums.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;

/**
 * Created by GeetaKrishna on 12/21/2015.
 */
@Slf4j
@Data
public final class User {

    static final int MAX_LENGTH_KITCHEN_NAME = 120;

    @Id
    private String userName;
    private String kitchenName;
    private String firstName;
    private String lastName;
    private Address personalAddress;
    private Address businessAddress;
    private String mobile;
    private Boolean enableTextMessaging = false;
    private Role role = Role.DEFAULT;
}

