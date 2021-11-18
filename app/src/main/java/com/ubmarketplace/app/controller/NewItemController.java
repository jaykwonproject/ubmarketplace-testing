package com.ubmarketplace.app.controller;

import com.ubmarketplace.app.dto.NewItemRequest;
import com.ubmarketplace.app.dto.NewItemResponse;
import com.ubmarketplace.app.manager.ImageManager;
import com.ubmarketplace.app.manager.ItemManager;
import com.ubmarketplace.app.model.Item;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Log
public class NewItemController {

    final ItemManager itemManager;
    final ImageManager imageManager;

    @Autowired
    public NewItemController(ItemManager itemManager, ImageManager imageManager) {
        this.itemManager = itemManager;
        this.imageManager = imageManager;
    }

    @RequestMapping(value = "/api/newItem", method = RequestMethod.POST)
    public NewItemResponse newItem(@RequestBody NewItemRequest newItemRequest) {
        log.info(String.format("newItem get call with %s", newItemRequest));
        Item item = itemManager.addItem(
                newItemRequest.getName(),
                newItemRequest.getUserId(),
                newItemRequest.getCategory(),
                newItemRequest.getDescription(),
                newItemRequest.getPrice(),
                newItemRequest.getImages(),
                newItemRequest.getMeetingPlace(),
                (newItemRequest.getContactPhoneNumber() == null) ? "" : newItemRequest.getContactPhoneNumber(),
                imageManager
        );

        return NewItemResponse.builder().item(item).build();
    }
}
