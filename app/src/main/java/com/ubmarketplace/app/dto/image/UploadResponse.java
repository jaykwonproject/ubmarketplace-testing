package com.ubmarketplace.app.dto.image;

import com.ubmarketplace.app.model.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UploadResponse {
    private String imageId;
    private Image image;
}
