package com.ubmarketplace.app.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDeleteRequest {

    @NotNull(message = "Password cannot be empty")
    private String itemID;
}