package com.ubmarketplace.app.dto;
import lombok.*;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CategoryItemRequest {
    @NotNull(message = "categorize cant be null")
    private String category;

    @NotNull(message = "userID cant be null")
    private String userId;

    @NotNull(message = "location cant be null")
    private String location;

    @NotNull(message = "pricing cant be null")
    private String pricing;
}
