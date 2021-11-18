package com.ubmarketplace.app.controller;

import com.ubmarketplace.app.dto.GetItemResponse;
import com.ubmarketplace.app.dto.ResponseItem;
import com.ubmarketplace.app.manager.ImageManager;
import com.ubmarketplace.app.manager.ItemManager;
import com.ubmarketplace.app.manager.UserManager;
import com.ubmarketplace.app.model.Item;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;

import static com.ubmarketplace.app.dto.ResponseItem.imageType.LARGE;

@RestController
@Log
@Validated
public class GetItemController {
    final ImageManager imageManager;
    final ItemManager itemManager;
    final UserManager userManager;

    @Autowired
    public GetItemController(ImageManager imageManager, ItemManager itemManager, UserManager userManager) {
        this.imageManager = imageManager;
        this.itemManager = itemManager;
        this.userManager = userManager;
    }

    @RequestMapping(value = "/api/getItem/{itemId}", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    public GetItemResponse getItem(
            @PathVariable
            @Pattern(regexp = "^[a-zA-Z0-9]{8}$", message = "ItemId format incorrect") String itemId) {

        log.info(String.format("Get item detail for %s", itemId));

        Item item = itemManager.getItemById(itemId);

        return GetItemResponse.builder()
                .item(new ResponseItem(item, LARGE, userManager, imageManager))
                .build();
    }
}
