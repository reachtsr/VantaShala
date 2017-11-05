package com.vs.rest.api.menu;

import com.vs.common.filters.AppConstants;
import com.vs.model.SaveFileModel;
import com.vs.model.enums.FileUploadTypeEnum;
import com.vs.model.enums.ItemStatus;
import com.vs.model.menu.Item;
import com.vs.rest.api.BaseController;
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
import java.util.Objects;

@Component
@Path("/menu/{menuId}/item")
@Slf4j
@Api(value = "Customer Menu's Item Management", description = "Item Controller")
public class ItemController extends BaseController {


    @Autowired
    IItemservice itemservice;

    @POST
    @ApiOperation(value = "Adds a new item to the menu", nickname = "addItem")
    public Response addMenuItem(@HeaderParam("userName") String userName, @PathParam("menuId") ObjectId menuId, Item item) throws Exception {
        Preconditions.checkNotNull(menuId);
        Preconditions.checkNotNull(userName);
        itemservice.addMenuItem(menuId, item);
        return build201Response(item);
    }

    @GET
    @Path("/{itemId}")
    @ApiOperation(value = "Get the Item for a user in the menu", nickname = "getItem")
    public Response getMenu(@HeaderParam("userName") String userName, @PathParam("menuId") ObjectId menuId, @PathParam("itemId") ObjectId id) throws Exception {
        Preconditions.checkNotNull(userName, "User Not found");
        Preconditions.checkNotNull(menuId, "MenuId is null");
        Preconditions.checkNotNull(id, "ItemId is null");

        Item item = itemservice.getMenuItem(menuId, id);
        if (Objects.isNull(item)) {
            return build404Response();
        }
        return build200Response(item);
    }


    @PUT
    @Path("/{itemId}")
    @ApiOperation(value = "Adds a new item to the menu", nickname = "addItem")
    public Response updateMenuItem(@HeaderParam("userName") String userName, @PathParam("menuId") ObjectId menuId, @PathParam("itemId") ObjectId itemId, Item item) throws Exception {
        Preconditions.checkNotNull(menuId, "Invalid Menu Id");
        Preconditions.checkNotNull(userName, "NO User Found");
        Preconditions.checkState(itemId.equals(item.getId()), "Item ids provided are not matched");
        itemservice.updateMenuItem(menuId, item);
        return build204Response();
    }

    @DELETE
    @Path("/{itemId}")
    @ApiOperation(value = "Checks and delete the item from a Menu", nickname = "addItem")
    public Response deleteMenuItem(@HeaderParam("userName") String userName, @PathParam("menuId") ObjectId menuId, @PathParam("itemId") ObjectId itemId) throws Exception {
        Preconditions.checkNotNull(menuId, "MenuId is null");
        Preconditions.checkNotNull(itemId, "ItemId is null");
        Preconditions.checkNotNull(userName, "Username not found");
        itemservice.deleteMenuItem(menuId, itemId);
        return build204Response();
    }

    @PUT
    @Path("/{itemId}/status/{status}")
    @ApiOperation(value = "Change the status of the Menu Item", nickname = "changeItemStatus")
    public Response updateMenuItemStatus(@HeaderParam("userName") String userName, @PathParam("menuId") ObjectId menuId, @PathParam("itemId") ObjectId itemId, @PathParam("status") ItemStatus status) {
        Preconditions.checkNotNull(menuId, "MenuId is null");
        Preconditions.checkNotNull(itemId, "ItemId is null");
        Preconditions.checkNotNull(userName, "Username not found");
        Preconditions.checkNotNull(status, "Status not found");
        itemservice.updateUserMenuItemStatus(menuId, itemId, status);
        return build204Response();
    }

    //Todo -- Reject those other than jpg, png, gif
    @POST
    @Path("/{itemId}/uploadItemPicture")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(value = "Uploads a picture for a given user/menu/item id", nickname = "uploadPicture")
    public Response uploadItemPicture(@HeaderParam("userName") String userName, @PathParam("menuId") ObjectId menuId, @PathParam("itemId") ObjectId itemId, @FormDataParam("picture") InputStream picture,
                                      @FormDataParam("picture") FormDataContentDisposition fileDisposition) throws Exception {


        log.info("Uploading Item Pic with size: {}", fileDisposition.getSize());

        Preconditions.checkArgument(!(fileDisposition.getSize() > AppConstants.MAX_PROFILE_SIZE), "Too Big File. Must be less than an MB.");
        SaveFileModel saveFile = new SaveFileModel();
        saveFile.setContentDisposition(fileDisposition);
        saveFile.setInputStream(picture);
        saveFile.setFileUploadTypeEnum(FileUploadTypeEnum.MENU_ITEM_PICTURE);
        saveFile.validate();

        itemservice.saveFile(menuId, itemId, saveFile);

        return build204Response();

    }
}
