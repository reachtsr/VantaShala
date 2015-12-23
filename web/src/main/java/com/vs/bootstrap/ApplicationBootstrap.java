package com.vs.bootstrap;

import com.vs.common.Bootstrap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Arrays;

/**
 * Created by GeetaKrishna on 11/8/2015.
 */


@SpringBootApplication
@Slf4j
@ComponentScan("com.vs")
@EnableMongoRepositories({"com.vs.repository"})
public class ApplicationBootstrap implements CommandLineRunner, ServletContextInitializer, Bootstrap {

    @Resource(name="serviceBootstrap")
    private Bootstrap serviceBootstrap;

    @Resource(name="APIBootstrap")
    private Bootstrap apiBootstrap;

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(ApplicationBootstrap.class, args);

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
        initialize();
    }

    @Override
    public void initialize() {
        serviceBootstrap.initialize();
        apiBootstrap.initialize();
        // Check for Mongo Collection
        // If none available create the Basic Collection for super admin
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        log.info("Servlet Context: {} {}.", servletContext.getContextPath(), servletContext.getRealPath("/"));
    }
}
