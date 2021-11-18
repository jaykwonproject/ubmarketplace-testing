package com.ubmarketplace.app.controller;

import com.ubmarketplace.app.dto.*;
import com.ubmarketplace.app.manager.ItemManager;
import com.ubmarketplace.app.manager.UserManager;
import com.ubmarketplace.app.model.User;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.InvalidParameterException;


@RestController
@Log
public class ItemDeleteController {
    final UserManager userManager;
    final ItemManager itemManager;

    @Autowired
    public ItemDeleteController(UserManager userManager, ItemManager itemManager) {
        this.userManager = userManager;
        this.itemManager = itemManager;
    }

    @RequestMapping(value = "/api/deleteitem", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public String delete(@RequestBody @Valid ItemDeleteRequest itemDeleteRequest){

        if (!itemManager.deleteItem(itemDeleteRequest.getItemID())){
            return "fail";
        }
        else{
            return "success";
        }
    }

}
