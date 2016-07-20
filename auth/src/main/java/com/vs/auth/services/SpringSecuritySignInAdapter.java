package com.vs.auth.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.PostConstruct;

/**
 * Created by GeetaKrishna on 1/22/2016.
 */
@Service
@Slf4j
public class SpringSecuritySignInAdapter implements SignInAdapter {


    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    SimpleSocialUsersDetailService socialUserDetailsService;


    @PostConstruct
    public void init(){
        log.info("=======> ___=++++++++++++++");
    }

    public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request) {


        log.info(" ====>  Setting the Security Context to Authenticated....");
try{

        SecurityContextHolder.getContext().setAuthentication(
                new SocialAuthenticationToken(connection, socialUserDetailsService, null, null));

    } finally {
        SecurityContextHolder.clearContext();
    }

        return null;
    }
}
