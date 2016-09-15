package com.vs.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vs.model.enums.Role;
import com.vs.model.enums.UserStatusEnum;
import com.vs.model.user.address.Address;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by GeetaKrishna on 12/21/2015.
 */
@Slf4j
@Data
@Document
public abstract class User implements SocialUserDetails {

    static final int MAX_LENGTH_KITCHEN_NAME = 120;

    @Id
    private String userName;
    private Role role = Role.DEFAULT;
    private String firstName;
    private String lastName;

    @Indexed(unique = true)
    private String email;
    private String mobile;
    private Address personalAddress;
    private UserStatusEnum status;
    private Boolean enableTextMessaging = false;

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("ROLE ADDED : {}", role.name());
        Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(1);
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }

    @Override
    public String getUserId() {
        return userName;
    }

    @Override
    public String getPassword() {
        return "ABCD";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public String getUserName() {
        return userName;
    }

}

