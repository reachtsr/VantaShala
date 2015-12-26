package com.vs.rest.api.menu;

import com.vs.model.enums.MenuStatus;
import com.vs.model.menu.Menu;
import com.vs.model.user.User;
import com.vs.rest.api.common.CommonController;
import com.vs.service.menu.IMenuService;
import jersey.repackaged.com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Component
@Path("/menu")
@Slf4j
public class MenuController {

    @Autowired
    IMenuService menuService;

    @GET
    @Path("/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserMenus(@PathParam("userName") String userName){
        Preconditions.checkNotNull(userName);
        List<Menu> menus = menuService.getUserMenus(userName);
        return CommonController.buildResponse(menus);
    }

    @GET
    @Path("/{userName}/{menuNameOrId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getMenus(@PathParam("userName") String userName, @PathParam("menuNameOrId") String menuNameOrId){
        Preconditions.checkNotNull(userName);
        List<Menu> menus =  menuService.getUserMenuByNameOrId(userName, menuNameOrId);
        return CommonController.buildResponse(menus);
    }


    @POST
    @Path("/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response createMenu(Menu menu){
        Preconditions.checkNotNull(menu.getUserName());
        Preconditions.checkNotNull(menu.getName());
        menuService.createUserMenu(menu);
        return CommonController.buildResponse("Menu Created");
    }

    @PUT
    @Path("/{userName}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateMenu(Menu menu){
        Preconditions.checkNotNull(menu.getUserName());
        Preconditions.checkNotNull(menu.getName());
        Preconditions.checkArgument(menuService.menuExists(menu.getMenuId()));
        menuService.updateUserMenu(menu);
        return CommonController.buildResponse("Menu Updated");

    }

    @DELETE
    @Path("/{userName}/{menuId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteMenu(@PathParam("userName") String userName, @PathParam("menuId") String menuId) {
        Preconditions.checkNotNull(userName);
        Preconditions.checkNotNull(menuId);
        menuService.deleteUserMenu(userName,menuId);
        return CommonController.buildResponse("Menu Deleted");
    }

    @POST
    @Path("status/{menuId}/{status}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateMenuStatus(@PathParam("menuId") String id, @PathParam("status") MenuStatus status){
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(status);
        menuService.updateUserMenuStatus(id, status);
        return CommonController.buildResponse("Menu Created");
    }

}
