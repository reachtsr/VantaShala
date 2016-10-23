package com.vs.repository.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by GeetaKrishna on 9/12/2016.
 */
@Slf4j
@Component
public class CustomerRepositoryAdapter {

    @PostConstruct
    public void init(){
      log.info("{} Initiated", this.getClass().getName());
    }
}
