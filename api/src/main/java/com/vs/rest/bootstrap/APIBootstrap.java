package com.vs.rest.bootstrap;

import com.vs.common.Bootstrap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by GeetaKrishna on 12/21/2015.
 */
@Component
@Slf4j
public class APIBootstrap implements Bootstrap {

    public void initialize() {
        log.info("Initializing the API Service...");
    }
}
