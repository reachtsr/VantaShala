package com.vs.bootstrap;

import com.vs.model.enums.FileUploadTypeEnum;
import com.vs.model.props.ReadYML;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.models.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by root on 12/31/16.
 */

@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Autowired
    ReadYML readYML;

    @PostConstruct
    public void configure() {

        swaggerConfiguration();
        System.setProperty("mail.mime.multipart.ignoreexistingboundaryparameter", "true");
        runConfigurations();
    }

    //     Jersey Swagger Configuration
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


    private void runConfigurations() {
        log.info("Running Configurations");
        createDirectorsRequired();
    }

    private void createDirectorsRequired() {
        log.info("Creating File Upload Directories");

        String rPath;
        String configurationPath = readYML.getBaseFileUploadLocation();
        for (FileUploadTypeEnum fileUploadTypeEnum : FileUploadTypeEnum.values()) {
            rPath = fileUploadTypeEnum.getPath();
            rPath = configurationPath + rPath; //String.join(configurationPath, rPath);
            Path path = Paths.get(rPath);
            log.info("Path Constructed: {}, CompletePath: {}", path.toString(), path.toAbsolutePath().toString());

            File f = new File(path.toAbsolutePath().toString());
            if (!f.exists()) {
                f.mkdirs();
            }
        }
    }
}
