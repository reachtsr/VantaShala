package com.vs.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.context.annotation.Configuration;

/**
 * Created by GeetaKrishna on 12/18/2015.
 */
@Slf4j
@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        log.info("Registering Jersey Configuration");
        register(RequestContextFilter.class);
        packages("com.vs");
//        @TODO move com.vs to application yml
        register(LoggingFilter.class);
    }
}
