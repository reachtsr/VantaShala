package com.vs.rest.api.admin;

import com.vs.model.menu.Menu;
import com.vs.rest.api.BaseController;
import com.vs.service.menu.IMenuService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Component
@Path("/admn/menu")
@Slf4j
@Api(value = "Menu Administration", description = "Admin Search Controller")
public class AdminMenuController extends BaseController {

    @Autowired
    IMenuService menuService;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getAllUserMenus() {
        List<Menu> menus = menuService.getMenus();
        return buildResponse(menus);
    }

}
