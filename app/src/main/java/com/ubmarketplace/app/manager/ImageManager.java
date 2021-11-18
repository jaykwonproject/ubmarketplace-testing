package com.ubmarketplace.app.manager;

import com.google.inject.Singleton;
import com.ubmarketplace.app.model.Image;
import com.ubmarketplace.app.repository.ImageRepository;
import com.ubmarketplace.app.repository.ImgBBRepository;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.security.InvalidParameterException;
import java.util.List;

import static com.ubmarketplace.app.Static.IMGBB_API_URL;
import static com.ubmarketplace.app.Static.IMGBB_EXPIRATION_SECS;

@Singleton
@Component
@Log
public class ImageManager {
    final ImageRepository imageRepository;
    @Value("${ImgBBApiKey: #{null}}")
    private String ImgBBApiKey = "";

    @Autowired
    public ImageManager(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image getImage(@NonNull String imageId) {
        if (imageId.isEmpty()) {
            log.info("Empty imageId when running getImage()");
            throw new InvalidParameterException("Empty imageId");
        }

        Image image = imageRepository.findById(imageId);

        if (image == null) {
            log.warning(String.format("Failed to find imageId %s, no such image exist", imageId));
            throw new InvalidParameterException("Failed to find image");
        }

        return image;
    }

    public String getThumbUrl(@NonNull String imageId) {
        return getImage(imageId).getThumb();
    }

    public String getMediumUrl(@NonNull String imageId) {
        return getImage(imageId).getMedium();
    }

    public String getLargeUrl(@NonNull String imageId) {
        return getImage(imageId).getLarge();
    }

    public Boolean isValidImageId(@NonNull String imageId) {
        return !(imageRepository.findById(imageId) == null);
    }

    public Boolean isValidImageIds(@NonNull List<String> imageIds) {
        for(String imageId : imageIds){
            if (!isValidImageId(imageId)){
                return false;
            }
        }
        return true;
    }

    public Image uploadAndInsertImage(@NonNull String imageInBase64, @NonNull String userId) {
        return uploadAndInsertImage(imageInBase64, userId, false);
    }

    public Image uploadAndInsertImage(@NonNull String imageInBase64, @NonNull String userId, Boolean test) {
        if (userId.isEmpty()) {
            log.info("Empty username when calling uploadAndInsertImage()");
            throw new InvalidParameterException("Invalid userId");
        }

        Image image = Image.builder().uploadBy(userId).build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("key", ImgBBApiKey);

        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("image", imageInBase64);
        bodyMap.add("key", ImgBBApiKey);
        bodyMap.add("name", image.getImageId());

        if (test) {
            bodyMap.add("expiration", IMGBB_EXPIRATION_SECS);
        }

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ImgBBRepository> response;
        try {
            response = restTemplate.exchange(IMGBB_API_URL, HttpMethod.POST, requestEntity, ImgBBRepository.class);
        } catch (HttpClientErrorException e) {
            log.warning(String.format("Unable to upload image: %s", e.getMessage()));
            throw new InvalidParameterException("Unable to upload image");
            // When testing locally, this could happen when you didn't set up environment variable ImgBBApiKey
            // To solve this: Go to Heroku to find the value of ImgBBApiKey and set it locally
        }


        if (response.getBody() == null) {
            log.warning("Response body is empty when uploading image");
            throw new NullPointerException("Error while upload image");
        }

        image.setThumb(response.getBody().getData().getThumb().getUrl());
        image.setLarge(response.getBody().getData().getImage().getUrl());

        if (response.getBody().getData().getMedium() == null) {
            image.setMedium(image.getLarge());
        } else {
            image.setMedium(response.getBody().getData().getMedium().getUrl());
        }

        insertImage(image); // Todo: add retry when failed

        return image;
    }

    public void insertImage(@NonNull Image image) {
        try {
            imageRepository.insert(image);
        } catch (DuplicateKeyException e) {
            log.warning(String.format("Failed to insert imageId %s, such image already exist", image.getImageId()));
            throw new InvalidParameterException("Failed to insert imageId");
            // Not the best exception, could update to a custom exception
        }
    }
}
