package com.ubmarketplace.app.controller.image;

import com.ubmarketplace.app.dto.image.UploadRequest;
import com.ubmarketplace.app.dto.image.UploadResponse;
import com.ubmarketplace.app.manager.ImageManager;
import com.ubmarketplace.app.model.Image;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Log
@Validated
public class UploadController {
    final ImageManager imageManager;

    @Autowired
    public UploadController(ImageManager imageManager) {
        this.imageManager = imageManager;
    }

    @RequestMapping(value = "/api/image/upload", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public UploadResponse upload(@RequestBody @Valid UploadRequest uploadRequest) {

        log.info(String.format("Receiving image upload request from %s", uploadRequest.getUserId()));

        Image image = imageManager.uploadAndInsertImage(uploadRequest.getImage(),
                uploadRequest.getUserId());

        return UploadResponse.builder()
                .imageId(image.getImageId())
                .image(image)
                .build();
    }
}
