package com.ubmarketplace.app.controller;

import com.ubmarketplace.app.dto.CategoryItemRequest;
import com.ubmarketplace.app.dto.CategoryItemResponse;
import com.ubmarketplace.app.dto.ResponseItem;
import com.ubmarketplace.app.manager.ImageManager;
import com.ubmarketplace.app.manager.ItemManager;
import com.ubmarketplace.app.manager.UserManager;
import com.ubmarketplace.app.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.ubmarketplace.app.dto.ResponseItem.imageType.THUMB;

@RestController
public class CategoryItemController {

    final ImageManager imageManager;
    final ItemManager itemManager;
    final UserManager userManager;

    @Autowired
    public CategoryItemController(ImageManager imageManager, ItemManager itemManager, UserManager userManager) {
        this.imageManager = imageManager;
        this.itemManager = itemManager;
        this.userManager = userManager;
    }

    @RequestMapping(value = "/api/categoryitem", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public CategoryItemResponse categorizeItem(@RequestBody CategoryItemRequest request){

        // Get the queryResult
        List<Item> queryResult = itemManager.getCategoryItem(request.getCategory(), request.getUserId(), request.getLocation(), request.getPricing());

        // Convert List<Item> to List<AllItemResponse.AllItemResponseItem>
        List<ResponseItem> response = queryResult.parallelStream()
                .map(item -> new ResponseItem(item, THUMB, userManager, imageManager))
                .collect(Collectors.toList());


        return CategoryItemResponse.builder().item(response).build();
    }
}
