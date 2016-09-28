package com.vs.bootstrap;

import com.vs.common.Bootstrap;
import com.vs.service.email.EmailService;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.web.WebApplicationInitializer;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.PostConstruct;
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
@EnableConfigurationProperties
@EnableScheduling
public class ApplicationBootstrap extends SpringBootServletInitializer implements Bootstrap {

    @Resource(name="serviceBootstrap")
    private Bootstrap serviceBootstrap;

    @Resource(name="APIBootstrap")
    private Bootstrap apiBootstrap;

    @Autowired
    private EmailService emailService;

    public static void main(String[] args) {

        ApplicationContext ctx = new ApplicationBootstrap().configure(new SpringApplicationBuilder(ApplicationBootstrap.class)).run(args);

        log.info("Scanning Initialized Beans...");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            log.trace(beanName);
        }

        //SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);

        log.info("Scanning Initialized Beans...Completed!");
        log.info(" ****   Hurray! Application Started.   ****");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApplicationBootstrap.class);
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

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        log.info("Servlet Context: {} {}.", servletContext.getContextPath(), servletContext.getRealPath("/"));
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }
}
