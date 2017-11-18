package com.vs.rest.api.menu;

import com.vs.model.menu.Menu;
import com.vs.rest.api.BaseController;
import com.vs.service.menu.IMenuService;
import com.vs.service.menu.item.IItemservice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jersey.repackaged.com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Objects;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Component
@Path("/menu")
@Slf4j
@Api(value = "Customer Menu Management", description = "Menu Controller")
public class MenuController extends BaseController {

    @Autowired
    IMenuService menuService;

    //@Todo Allow only menu creation for a given duration/verify based on the date.
    @POST
    @ApiOperation(value = "Create Menu", nickname = "createMenu")
    public Response createMenu(@HeaderParam("userName") String userName, Menu menu) {
        Preconditions.checkNotNull(userName);
        Preconditions.checkNotNull(menu.getName());
        Preconditions.checkNotNull(menu.getCutOffHours());
        Preconditions.checkNotNull(menu.getEndDate());
        menuService.createUserMenu(userName, menu);
        return build201Response(menu);
    }

    @GET
    @Path("/{menuId}")
    @ApiOperation(value = "Get the Menus for a user by menu Id", nickname = "getMenu")
    public Response getMenu(@HeaderParam("userName") String userName, @PathParam("menuId") ObjectId id) {
        Preconditions.checkNotNull(userName, "User Not found");
        Menu menu = menuService.getUserMenuById(userName, id);
        if (Objects.isNull(menu)) {
            return build404Response();
        }
        return build200Response(menu);
    }

    @GET
    @Path("/name/{menuName}")
    @ApiOperation(value = "Get the Menus for a user by menu name", nickname = "getMenu")
    public Response getMenuByName(@HeaderParam("userName") String userName, @PathParam("menuName") String menuName) {
        Preconditions.checkNotNull(userName);
        List<Menu> menus = menuService.getUserMenuByName(userName, menuName);
        return build200Response(menus);
    }


    @GET
    @Path("/list")
    @ApiOperation(value = "Get All Menus for this user", nickname = "getMenu")
    public Response getUserMenus(@HeaderParam("userName") String userName) {
        Preconditions.checkNotNull(userName);
        List<Menu> menus = menuService.getUserMenus(userName);
        return build200Response(menus);
    }


    @PUT
    @ApiOperation(value = "Update a Menu for this user by Id", nickname = "updateMenu")
    public Response updateMenu(@HeaderParam("userName") String userName, Menu menu) throws Exception {

        Preconditions.checkNotNull(userName, "No user found");
        Preconditions.checkNotNull(menu.getName(), "Invalid menu Name");

        menuService.updateUserMenu(userName, menu);
        return build204Response();

    }

    @DELETE
    @Path("/{menuId}")
    @ApiOperation(value = "Deletes  the menu for a given user by menu id", nickname = "delete menu")
    public Response deleteMenu(@HeaderParam("userName") String userName, @PathParam("menuId") ObjectId menuId) throws Exception {
        Preconditions.checkNotNull(userName);
        Preconditions.checkNotNull(menuId);
        menuService.deleteUserMenu(userName, menuId);
        return build204Response();
    }


}
