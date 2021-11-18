package com.ubmarketplace.app.controller;

import com.ubmarketplace.app.dto.LoginRequest;
import com.ubmarketplace.app.dto.LoginResponse;
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
public class LoginController {
    final UserManager userManager;

    @Autowired
    public LoginController(UserManager userManager) {
        this.userManager = userManager;
    }

    @RequestMapping(value = "/api/login", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {

        log.info(String.format("Receiving login request from %s", loginRequest.getUserId()));

        boolean validPassword = userManager.loginVerification(loginRequest.getUserId(), loginRequest.getPassword());

        if (!validPassword) {
            throw new InvalidParameterException("Username and password doesn't match");
        }

        return LoginResponse.builder()
                .user(User.builder()
                        .userId(loginRequest.getUserId())
                        .role(userManager.getUserRole(loginRequest.getUserId()))
                        .displayName(userManager.getDisplayName(loginRequest.getUserId()))
                        .build())
                .build();
    }

}
