package com.vs.auth.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by GeetaKrishna on 1/11/2016.
 */

@Service
@Slf4j
public class AuthSuccessHandler implements ApplicationListener<AuthenticationSuccessEvent>
{

   // @Autowired
  //  private StateService stateService;


    @PostConstruct
   public void init(){
        log.info("{} Initilaized.",AuthSuccessHandler.class.getName());
    }


    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event)
    {
        log.info("------>------++++++++++============> {}",event.getAuthentication());
       // System.out.println("rowCount = "+stateService.rowCount());

        try {
            SecurityContext ctx = SecurityContextHolder.createEmptyContext();
            SecurityContextHolder.setContext(ctx);
            ctx.setAuthentication(event.getAuthentication());

            log.info(" ===> Authentication updated {}",event.getAuthentication());

            //Do what ever you want to do

        } finally {
            SecurityContextHolder.clearContext();
        }

    }

}
