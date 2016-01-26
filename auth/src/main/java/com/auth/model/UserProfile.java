package com.auth.model;

import com.vs.model.enums.Role;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import java.util.Collection;
import java.util.HashSet;


/**
 * Created by GeetaKrishna on 1/9/2016.
 */
@Document(collection = "UserProfile")
@Data
@Slf4j
public class UserProfile implements SocialUserDetails {

    @Id
    private  String userId;

    private String name;

    private  String firstName;

    private  String lastName;

    private  String email;

   private  String username;

    private  String password="GOPI";

    private Role role = Role.DEFAULT;

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("ROLE ADDED : {}", role.name());
        Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(1);
        authorities.add(new SimpleGrantedAuthority(role.name()));
        return authorities;
    }



    @Override
    public String getPassword() {
        return password;
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
}