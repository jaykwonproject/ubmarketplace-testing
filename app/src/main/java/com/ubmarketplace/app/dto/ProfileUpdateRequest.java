package com.ubmarketplace.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProfileUpdateRequest {

    @NotNull(message = "Username cannot be empty")
    @Pattern(regexp="^[a-zA-Z0-9_]+@(buffalo.edu|test.com)$", message = "Format incorrect: Username should be a buffalo.edu email")
    private String username;

    @NotNull(message = "Password cannot be empty")
    @Size(min = 64, max = 64, message = "Password must hash with SHA-256 and should be 64 characters long")
    private String password;

    @NotNull(message = "Display name cannot be empty")
    @Size(min = 1, max = 16, message = "Displayname should between 1 to 25 characters")
    private String displayName;

}