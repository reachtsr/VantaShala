package com.vs.bootstrap;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.jersey.config.JerseyJaxrsConfig;
import io.swagger.models.Info;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;


/**
 * Created by GeetaKrishna on 12/18/2015.
 */
@Slf4j
@Configuration
@EnableSwagger2
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        log.info("Registering Jersey Configuration");
        register(RequestContextFilter.class);
        register(MultiPartFeature.class);
        packages("com.vs");
    }

    @PostConstruct
    public void configure() {
        register(ApiListingResource.class);
        register(SwaggerSerializers.class);
        register(WadlResource.class);

        swaggerConfiguration();
    }
    // Jersey Swagger Configuration
    private void swaggerConfiguration() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setSchemes(new String[]{"http", "https"});
        beanConfig.setBasePath("/vs/rest");
        beanConfig.setResourcePackage("com.vs.rest.api");
        beanConfig.setPrettyPrint(true);
        beanConfig.setScan(true);

        Info info = new Info();
        info.setTitle("VantaShala.com - API");
        info.setDescription("EAT Healthy so that Live Healthy");
        info.setVersion("1.0.0");

        beanConfig.setInfo(info);
    }
}



