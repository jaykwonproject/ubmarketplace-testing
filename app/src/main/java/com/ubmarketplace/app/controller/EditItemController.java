package com.ubmarketplace.app.controller;

import com.google.inject.Singleton;
import com.ubmarketplace.app.dto.EditItemRequest;
import com.ubmarketplace.app.manager.ImageManager;
import com.ubmarketplace.app.manager.ItemManager;
import com.ubmarketplace.app.manager.UserManager;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Singleton
@RestController
@Validated
@Log
public class EditItemController {
    final ImageManager imageManager;
    final ItemManager itemManager;
    final UserManager userManager;

    @Autowired
    public EditItemController(ImageManager imageManager, ItemManager itemManager, UserManager userManager) {
        this.imageManager = imageManager;
        this.itemManager = itemManager;
        this.userManager = userManager;
    }

    @RequestMapping(value = "/api/editItem", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public void editItem(@RequestBody @Valid EditItemRequest editItemRequest) {
        log.info(String.format("editItem get call with %s", editItemRequest));
        itemManager.editItem(
                editItemRequest.getItem().getItemId(),
                editItemRequest.getItem().getName(),
                editItemRequest.getItem().getCategory(),
                editItemRequest.getItem().getDescription(),
                editItemRequest.getItem().getPrice(),
                editItemRequest.getItem().getImages(),
                editItemRequest.getItem().getMeetingPlace(),
                editItemRequest.getItem().getContactPhoneNumber(),
                editItemRequest.getUserId(),
                userManager,
                imageManager);
    }
}
