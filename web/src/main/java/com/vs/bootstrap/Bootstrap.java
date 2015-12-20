package com.vs.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GeetaKrishna on 11/8/2015.
 */


@SpringBootApplication
@Slf4j
@ComponentScan("com.vs")

public class Bootstrap implements CommandLineRunner, ServletContextInitializer {
    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(Bootstrap.class, args);

        log.info("Scanning Initialized Beans...");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            log.trace(beanName);
        }
        log.info("Scanning Initialized Beans...Done!");
        log.info("Hurray! Application Started. ");
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("After application initialized...");

        // Check for Mongo Collection
        // If none available create the Basic Collection for super admin
    }



    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        log.info("Servlet Context: {} {}.", servletContext.getContextPath(), servletContext.getRealPath("/"));
    }
}
