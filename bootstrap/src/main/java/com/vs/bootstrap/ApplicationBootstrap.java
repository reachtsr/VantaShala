package com.vs.bootstrap;

import com.vs.common.Bootstrap;
import com.vs.service.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;


/**
 * Created by GeetaKrishna on 11/8/2015.
 */


@SpringBootApplication
@Slf4j
@ComponentScan("com.vs")
@EnableMongoRepositories({"com.vs.repository"})
@EnableConfigurationProperties
@EnableScheduling
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ApplicationBootstrap implements Bootstrap {

    @Resource(name="serviceBootstrap")
    private Bootstrap serviceBootstrap;

    @Resource(name="APIBootstrap")
    private Bootstrap apiBootstrap;

    @Autowired
    private EmailService emailService;



    public static void main(String[] args) {

        ApplicationContext ctx = new VSServletInitializer().configure(new SpringApplicationBuilder(ApplicationBootstrap.class)).run(args);

        log.info("Scanning Initialized Beans...");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            log.trace(beanName);
        }

        //SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);

        log.info("Scanning Initialized Beans...Completed!");
        log.info(" ****   ****************************   ****");
        log.info(" ****   Hurray! Application Started.   ****");
        log.info(" ****   ****************************   ****");
    }

    @PostConstruct
    public void init() {

        log.debug("ApplicationBootstrap Loaded.");
        initialize();
    }

    @Override
    public void initialize() {
        log.info("Initializing Services...");
        serviceBootstrap.initialize();
        apiBootstrap.initialize();
        // Check for Mongo Collection
        // If none available create the Basic Collection for super admin

        emailService.sendAppStatusEmail();
    }


    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

}
