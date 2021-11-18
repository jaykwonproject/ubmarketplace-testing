package com.ubmarketplace.app.controller;

import com.ubmarketplace.app.dto.AllItemResponse;
import com.ubmarketplace.app.dto.ResponseItem;
import com.ubmarketplace.app.manager.ImageManager;
import com.ubmarketplace.app.manager.ItemManager;
import com.ubmarketplace.app.manager.UserManager;
import com.ubmarketplace.app.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.ubmarketplace.app.dto.ResponseItem.imageType.THUMB;

@RestController
public class AllItemController {

    final ImageManager imageManager;
    final ItemManager itemManager;
    final UserManager userManager;

    @Autowired
    public AllItemController(ImageManager imageManager, ItemManager itemManager, UserManager userManager) {
        this.imageManager = imageManager;
        this.itemManager = itemManager;
        this.userManager = userManager;
    }

    @RequestMapping(value = "/api/allitem", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public AllItemResponse allItem() {

        // Get the queryResult
        List<Item> queryResult = itemManager.getAllItem();

        // Convert List<Item> to List<AllItemResponse.AllItemResponseItem>
        List<ResponseItem> response = queryResult.parallelStream()
                .map(item -> new ResponseItem(item, THUMB, userManager, imageManager))
                .collect(Collectors.toList());

        return AllItemResponse.builder().item(response).build();
    }

}
