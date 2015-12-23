package com.vs.rest.api.menu;

import com.vs.model.menu.Menu;
import com.vs.rest.api.common.CommonController;
import com.vs.service.menu.IMenuService;
import jersey.repackaged.com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Component
@Path("/admin/menu")
@Slf4j
public class AdminMenuController {

    @Autowired
    IMenuService menuService;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserMenus(){
        List<Menu> menus = menuService.getMenus();
        return CommonController.buildResponse(menus);
    }

}
