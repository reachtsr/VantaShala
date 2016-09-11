package com.vs.bootstrap;

import com.vs.common.Bootstrap;
import com.vs.service.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.context.SecurityContextHolder;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Arrays;

/**
 * Created by GeetaKrishna on 11/8/2015.
 */

//@TODO FIX ALL THE TESTS : BUILD THE APP AND THEY FAIL.
@SpringBootApplication
@Slf4j
@ComponentScan("com.vs")
@EnableMongoRepositories({"com.vs.repository", "com.vs.auth.repository"})
@EnableConfigurationProperties
@EnableScheduling
public class ApplicationBootstrap implements ServletContextInitializer, Bootstrap {

    @Resource(name="serviceBootstrap")
    private Bootstrap serviceBootstrap;

    @Resource(name="APIBootstrap")
    private Bootstrap apiBootstrap;

    @Autowired
    private EmailService emailService;

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(ApplicationBootstrap.class, args);

        log.info("Scanning Initialized Beans...");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            log.trace(beanName);
        }

        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);

        log.info("Scanning Initialized Beans...Completed!");
        log.info(" ****   Hurray! Application Started.   ****");
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

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("greetings")
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.regex("vs/*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("VantaShala.com, Feel Healthy and Happy")
                .description("API Description")
                .termsOfServiceUrl("http:/vanatashala.com/software/sla/sladb.nsf/sla/bm?Open")
                .license("Contact Us for any License")
                .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
                .version("1.0")
                .build();
    }
}
