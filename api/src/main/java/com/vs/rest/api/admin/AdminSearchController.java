package com.vs.rest.api.admin;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.ws.rs.Path;

/**
 * Created by GeetaKrishna on 9/27/2016.
 */
@Component
@Path("/admn/search")
@Slf4j
@Api(value = "/admn/search", description = "Admin Search Controller")
public class AdminSearchController {
}
