package com.vs.rest.api.menu;

import com.vs.common.filters.AppConstants;
import com.vs.model.SaveFileModel;
import com.vs.model.enums.FileUploadTypeEnum;
import com.vs.model.enums.ItemStatus;
import com.vs.model.menu.Item;
import com.vs.model.menu.Menu;
import com.vs.rest.api.BaseController;
import com.vs.service.menu.IMenuService;
import com.vs.service.menu.item.IItemservice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jersey.repackaged.com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Component
@Path("/menu")
@Slf4j
@Api(value = "Customer Menu Management", description = "Menu Controller")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MenuController extends BaseController {

    @Autowired
    IMenuService menuService;

    @Autowired
    IItemservice itemservice;

    //@Todo Allow only menu creation for a given duration/verify based on the date.
    @POST
    @ApiOperation(value = "Create Menu", nickname = "createMenu")
    public Menu createMenu(@HeaderParam("userName") String userName, Menu menu) {
        Preconditions.checkNotNull(userName);
        Preconditions.checkNotNull(menu.getName());
        Preconditions.checkNotNull(menu.getCutOffHours());
        Preconditions.checkNotNull(menu.getEndDate());
        menuService.createUserMenu(userName, menu);
        return menu;
        //return buildResponse("Menu Created: " + menu.getId());
    }

    @GET
    @Path("/{menuId}")
    @ApiOperation(value = "Get the Menus for a user by menu Id", nickname = "getMenu")
    public Response getMenu(@HeaderParam("userName") String userName, @PathParam("menuId") ObjectId id) {
        Preconditions.checkNotNull(userName, "User Not found");
        Menu menu = menuService.getUserMenuById(userName, id);
        Preconditions.checkNotNull(menu, "No Menu found");
        return buildResponse(menu);
    }

    @GET
    @Path("/name/{menuName}")
    @ApiOperation(value = "Get the Menus for a user by menu name", nickname = "getMenu")
    public Response getMenuByName(@HeaderParam("userName") String userName, @PathParam("menuName") String menuName) {
        Preconditions.checkNotNull(userName);
        List<Menu> menus = menuService.getUserMenuByName(userName, menuName);
        return buildResponse(menus);
    }


    @GET
    @Path("/list")
    @ApiOperation(value = "Get All Menus for this user", nickname = "getMenu")
    public Response getUserMenus(@HeaderParam("userName") String userName) {
        Preconditions.checkNotNull(userName);
        List<Menu> menus = menuService.getUserMenus(userName);
        return buildResponse(menus);
    }


    @PUT
    @ApiOperation(value = "Update a Menu for this user by Id", nickname = "updateMenu")
    public Response updateMenu(@HeaderParam("userName") String userName, Menu menu) throws Exception {

        Preconditions.checkNotNull(userName, "No user found");
        Preconditions.checkNotNull(menu.getName(), "Invalid menu Name");

        menuService.updateUserMenu(userName, menu);
        return buildResponse("Menu Updated: " + menu.getId());

    }

    @POST
    @Path("/upload/itemPicture/{userName}/{menuId}/{itemId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(value = "Uploads a picture for a given user/menu/item id", nickname = "uploadPicture")
    public Response uploadItemPicture(@PathParam("userName") String userName, @PathParam("menuId") ObjectId menuId, @PathParam("itemId") ObjectId itemId, @FormDataParam("file") InputStream file,
                                      @FormDataParam("file") FormDataContentDisposition fileDisposition) throws Exception {


        log.info("Uploading Item Pic with size: {}", fileDisposition.getSize());

        Preconditions.checkArgument(!(fileDisposition.getSize() > AppConstants.MAX_PROFILE_SIZE), "Too Big File.");
        SaveFileModel saveFile = new SaveFileModel();
        saveFile.setContentDisposition(fileDisposition);
        saveFile.setInputStream(file);
        saveFile.setFileUploadTypeEnum(FileUploadTypeEnum.MENU_ITEM_PICTURE);
        saveFile.validate();

        itemservice.saveFile(menuId, itemId, saveFile);

        return Response.status(200).build();

    }

    @DELETE
    @Path("/{userName}/{menuId}")
    @ApiOperation(value = "Deletes  a picture for a given user/menu/item id", nickname = "uploadPicture")
    public Response deleteMenu(@PathParam("userName") String userName, @PathParam("menuId") ObjectId menuId) throws Exception {
        Preconditions.checkNotNull(userName);
        Preconditions.checkNotNull(menuId);
        menuService.deleteUserMenu(userName, menuId);
        return buildResponse("Menu Deleted: " + menuId);
    }

    @POST
    @Path("status/{menuId}/{itemId}/{status}")
    @ApiOperation(value = "Change the status of the Menu Item", nickname = "changeItemStatus")
    public Response updateMenuItemStatus(@PathParam("menuId") ObjectId menuId, @PathParam("itemId") ObjectId itemId, @PathParam("status") ItemStatus status) {
        Preconditions.checkNotNull(menuId);
        Preconditions.checkNotNull(itemId);
        Preconditions.checkNotNull(status);
        itemservice.updateUserMenuItemStatus(menuId, itemId, status);
        return buildResponse("Item Status Updated: id" + menuId + "itemId: " + itemId);
    }

    @PUT
    @Path("/{userName}/{menuId}")
    @ApiOperation(value = "Adds a new item to the menu", nickname = "addItem")
    public Response addMenuItem(@PathParam("userName") String userName, @PathParam("menuId") ObjectId menuId, Item item) throws Exception {
        Preconditions.checkNotNull(menuId);
        Preconditions.checkNotNull(userName);
        itemservice.addMenuItem(menuId, item);
        return buildResponse("Item Added: {}, Menu:{} " + menuId );
    }

    @DELETE
    @Path("/{userName}/{menuId}/{itemId}")
    @ApiOperation(value = "Checks and delete the item from a Menu", nickname = "addItem")
    public Response deleteMenuItem(@PathParam("userName") String userName, @PathParam("menuId") ObjectId menuId, @PathParam("itemId") ObjectId itemId) throws Exception {
        Preconditions.checkNotNull(menuId);
        Preconditions.checkNotNull(userName);
        itemservice.deleteMenuItem(menuId, itemId);
        return buildResponse("Item Added: {}, Menu:{} " + menuId );
    }

}
