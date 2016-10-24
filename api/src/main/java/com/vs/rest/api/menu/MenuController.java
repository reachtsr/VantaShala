package com.vs.rest.api.menu;

import com.vs.model.enums.MenuStatus;
import com.vs.model.menu.Menu;
import com.vs.rest.api.BaseController;
import com.vs.service.menu.IMenuService;
import jersey.repackaged.com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Component
@Path("/menu")
@Slf4j
public class MenuController extends BaseController {

    @Autowired
    IMenuService menuService;

    @GET
    @Path("/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserMenus(@PathParam("userName") String userName) {
        Preconditions.checkNotNull(userName);
        List<Menu> menus = menuService.getUserMenus(userName);
        return buildResponse(menus);
    }

    @GET
    @Path("/{userName}/{menuNameOrId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMenus(@PathParam("userName") String userName, @PathParam("menuNameOrId") String menuNameOrId) {
        Preconditions.checkNotNull(userName);
        List<Menu> menus = menuService.getUserMenuByNameOrId(userName, menuNameOrId);
        return buildResponse(menus);
    }

    @POST
        @Path("/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createMenu(Menu menu) {
        Preconditions.checkNotNull(menu.getUserName());
        Preconditions.checkNotNull(menu.getName());
        Preconditions.checkNotNull(menu.getStartDate());
        Preconditions.checkNotNull(menu.getEndDate());
        menuService.createUserMenu(menu);
        return buildResponse("Menu Created: "+menu.getMenuId());
    }

    @PUT
    @Path("/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateMenu(Menu menu) throws Exception{
        Preconditions.checkNotNull(menu.getUserName());
        Preconditions.checkNotNull(menu.getName());

        Thread.sleep(1000);

        boolean status = menuService.menuExists(menu.getMenuId());
        log.info("menuId: {} - {}", menu.getMenuId(), status);
        Preconditions.checkArgument(status);
        menuService.updateUserMenu(menu);
        return buildResponse("Menu Updated: "+menu.getMenuId());

    }

    @DELETE
    @Path("/{userName}/{menuId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteMenu(@PathParam("userName") String userName, @PathParam("menuId") String menuId) throws Exception {
        Preconditions.checkNotNull(userName);
        Preconditions.checkNotNull(menuId);
        menuService.deleteUserMenu(userName, menuId);
        return buildResponse("Menu Deleted: "+ menuId);
    }

    @POST
    @Path("status/{menuId}/{status}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateMenuStatus(@PathParam("menuId") String id, @PathParam("status") MenuStatus status) {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(status);
        menuService.updateUserMenuStatus(id, status);
        return buildResponse("Menu Updated: " + id);
    }

}
