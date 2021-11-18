package com.ubmarketplace.app.dto;

import com.ubmarketplace.app.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProfileUpdateResponse {
    private User user;
}
