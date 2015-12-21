package com.vs.service.bootstrap;

import com.vs.common.Bootstrap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by GeetaKrishna on 12/21/2015.
 */
@Component
@Slf4j
public class ServiceBootstrap implements Bootstrap{

    public void initialize(){
        log.info("Initializing the Services Service...");
        checkMongoCollectionAvailability();
    }

    private void checkMongoCollectionAvailability() {
        log.info("Checking VantaShala Collection ...");

    }
}
