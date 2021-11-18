package com.ubmarketplace.app.dto.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UploadRequest {
    @NotNull(message = "userId cannot be empty")
    @Pattern(regexp="^[a-zA-Z0-9_]+@(buffalo.edu|test.com)$",
            message = "Format incorrect: userId(Username) should be a buffalo.edu email")
    private String userId;

    @NotNull(message = "Image cannot be empty")
    @Pattern(regexp="^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$",
            message = "Format incorrect: Base64 format incorrect")
    @Length(max = 14000000, message = "Image should not larger then 10 Mb")
    private String image;
}
