package com.vs.bootstrap;

import com.vs.rest.api.UserController;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

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
        register(LoggingFilter.class);
    }
}
