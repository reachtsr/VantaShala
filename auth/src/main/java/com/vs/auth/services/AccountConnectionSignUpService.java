package com.vs.auth.services;

import com.vs.auth.repository.AUserRepository;
import com.vs.model.enums.Role;
import com.vs.model.user.Cook;
import com.vs.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Slf4j
@Component
public class AccountConnectionSignUpService implements ConnectionSignUp {

    private final AUserRepository usersDao;

    @Autowired
    public AccountConnectionSignUpService(AUserRepository usersDao) {
        this.usersDao = usersDao;
    }

    @Autowired
    SocialUserDetailsService socialUserDetailsService;


    @Autowired
    AUserRepository userRepository;

    @Override
    public String execute(Connection<?> connection)  {
        ConnectionData data = connection.createData();
        String imgURL = data.getImageUrl()+"?type=large";
        org.springframework.social.connect.UserProfile profile = connection.fetchUserProfile();
        String userId = data.getProviderUserId();
        log.info("Creating user-id: " + userId);
        User pf = new Cook();
        pf.setRole(Role.DEFAULT);
        try {
            BeanUtils.copyProperties(pf, profile);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        pf.setUserName(userId);
        userRepository.save(pf);
        log.info("Sec Context : {}", SecurityContextHolder.getContext().getAuthentication());
        log.info("User Created");
        return userId;
    }

}