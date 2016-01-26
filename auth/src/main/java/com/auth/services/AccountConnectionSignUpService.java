package com.auth.services;

import com.auth.model.UserProfile;
import com.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Slf4j
@Component
public class AccountConnectionSignUpService implements ConnectionSignUp {

    private final UserRepository usersDao;

    @Autowired
    public AccountConnectionSignUpService(UserRepository usersDao) {
        this.usersDao = usersDao;
    }

    @Autowired
    SocialUserDetailsService socialUserDetailsService;


    @Autowired
    UserRepository userRepository;

    @Override
    public String execute(Connection<?> connection)  {
        ConnectionData data = connection.createData();
        String imgURL = data.getImageUrl()+"?type=large";
        org.springframework.social.connect.UserProfile profile = connection.fetchUserProfile();
        String userId = data.getProviderUserId();
        log.info("Creating user-id: " + userId);
        UserProfile pf = new UserProfile();
        try {
            BeanUtils.copyProperties(pf, profile);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        pf.setUserId(userId);
        pf.setUsername(userId);
        userRepository.save(pf);
        log.info("Sec Context : {}", SecurityContextHolder.getContext().getAuthentication());
        log.info("User Created");
        return userId;
    }

}