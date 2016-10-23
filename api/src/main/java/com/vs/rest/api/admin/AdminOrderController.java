package com.vs.rest.api.admin;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;

/**
 * Created by GeetaKrishna on 9/27/2016.
 */
@Component
@Path("/admn/order")
@Slf4j
@Api(value = "/admn/order", description = "Admin Order Controller")
public class AdminOrderController {
}
