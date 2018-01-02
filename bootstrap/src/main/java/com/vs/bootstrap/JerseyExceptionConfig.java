package com.vs.bootstrap;

import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import java.util.Map;

/**
 * Created by GeetaKrishna on 12/18/2015.
 */
@Slf4j
@Component
public class JerseyExceptionConfig extends ResourceConfig {

    @Autowired
    ApplicationContext appCtx;

    @PostConstruct
    public void setup() {
        log.info("Registering Jersey & Multipart Configuration");

        //packages("com.vs");
        register(RequestContextFilter.class);
        register(MultiPartFeature.class);

        register(ApiListingResource.class);
        register(SwaggerSerializers.class);
        register(WadlResource.class);

        Map<String,Object> beans = appCtx.getBeansWithAnnotation(Path.class);
        for (Object o : beans.values()) {
            log.info("Registering: {}" ,o.getClass().getName());
            register(o);
        }

        Map<String,Object> exceptions = appCtx.getBeansWithAnnotation(Provider.class);
        for (Object o : exceptions.values()) {
            log.info("Registering Exception Mapper --> {}" ,o.getClass().getName());
            register(o);
        }


    }
}



