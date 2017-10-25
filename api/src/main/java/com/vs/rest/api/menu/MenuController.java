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
import jersey.repackaged.com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
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


public class MenuController extends BaseController {

    @Autowired
    IMenuService menuService;

    @Autowired
    IItemservice itemservice;

    @POST
    @Path("/{userName}")
    public Response createMenu(Menu menu) {
        Preconditions.checkNotNull(menu.getUserName());
        Preconditions.checkNotNull(menu.getName());
        Preconditions.checkNotNull(menu.getCutOffHours());
        Preconditions.checkNotNull(menu.getEndDate());
        menuService.createUserMenu(menu);
        return buildResponse("Menu Created: " + menu.getMenuId());
    }

    @GET
    @Path("/{userName}")
    public Response getUserMenus(@PathParam("userName") String userName) {
        Preconditions.checkNotNull(userName);
        List<Menu> menus = menuService.getUserMenus(userName);
        return buildResponse(menus);
    }

    @GET
    @Path("/{userName}/{menuNameOrId}")
    public Response getMenu(@PathParam("userName") String userName, @PathParam("menuNameOrId") String menuNameOrId) {
        Preconditions.checkNotNull(userName);
        List<Menu> menus = menuService.getUserMenuByNameOrId(userName, menuNameOrId);
        return buildResponse(menus);
    }


    @PUT
    @Path("/{userName}")
    public Response updateMenu(Menu menu) throws Exception {
        Preconditions.checkNotNull(menu.getUserName());
        Preconditions.checkNotNull(menu.getName());

        menuService.updateUserMenu(menu);
        return buildResponse("Menu Updated: " + menu.getMenuId());

    }

    @POST
    @Path("/upload/itemPicture/{userName}/{menuId}/{itemId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadItemPicture(@PathParam("userName") String userName, @PathParam("menuId") String menuId, @PathParam("itemId") String itemId, @FormDataParam("file") InputStream file,
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
    public Response deleteMenu(@PathParam("userName") String userName, @PathParam("menuId") String menuId) throws Exception {
        Preconditions.checkNotNull(userName);
        Preconditions.checkNotNull(menuId);
        menuService.deleteUserMenu(userName, menuId);
        return buildResponse("Menu Deleted: " + menuId);
    }

    @POST
    @Path("status/{menuId}/{itemId}/{status}")
    public Response updateMenuItemStatus(@PathParam("menuId") String menuId, @PathParam("itemId") String itemId, @PathParam("status") ItemStatus status) {
        Preconditions.checkNotNull(menuId);
        Preconditions.checkNotNull(itemId);
        Preconditions.checkNotNull(status);
        itemservice.updateUserMenuItemStatus(menuId, itemId, status);
        return buildResponse("Item Status Updated: menuId" + menuId + "itemId: " + itemId);
    }

    @PUT
    @Path("/{userName}/{menuId}")
    public Response addMenuItem(@PathParam("userName") String userName, @PathParam("menuId") String menuId, Item item) throws Exception {
        Preconditions.checkNotNull(menuId);
        Preconditions.checkNotNull(userName);
        itemservice.addMenuItem(menuId, item);
        return buildResponse("Item Added: {}, Menu:{} " + menuId );
    }

    @DELETE
    @Path("/{userName}/{menuId}/{itemId}")
    public Response deleteMenuItem(@PathParam("userName") String userName, @PathParam("menuId") String menuId, @PathParam("itemId") String itemId) throws Exception {
        Preconditions.checkNotNull(menuId);
        Preconditions.checkNotNull(userName);
        itemservice.deleteMenuItem(menuId, itemId);
        return buildResponse("Item Added: {}, Menu:{} " + menuId );
    }

}
