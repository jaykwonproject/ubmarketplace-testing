package com.ubmarketplace.app.manager;

import com.ubmarketplace.app.model.Image;
import com.ubmarketplace.app.repository.ImageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.security.InvalidParameterException;

import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_1;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_2;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_BASE64;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_IMAGE_ID_1;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_IMAGE_ID_2;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_IMAGE_ID_INVALID;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_LARGE_1;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_MEDIUM_1;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_THUMB_1;
import static com.ubmarketplace.app.TestStatic.TEST_USER_ID_1;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ImageManagerTest {
    @Autowired
    ImageManager imageManager;

    @Value("${ImgBBApiKey: #{null}}")
    private String ImgBBApiKey = "";

    @BeforeEach
    void setup(@Autowired ImageRepository imageRepository) {
        imageRepository.insert(TEST_IMAGE_1);
    }

    @Test
    public void GIVEN_validImageId_WHEN_getImage_THEN_returnImage() {
        Image image = imageManager.getImage(TEST_IMAGE_IMAGE_ID_1);
        Assertions.assertEquals(TEST_IMAGE_1, image);
    }

    @Test
    public void GIVEN_InvalidImageId_WHEN_getImage_THEN_throwException() {
        Assertions.assertThrows(InvalidParameterException.class, () -> imageManager.getImage(TEST_IMAGE_IMAGE_ID_INVALID));
    }

    @Test
    public void GIVEN_ValidImage_WHEN_uploadAndInsertImage_THEN_returnImage() {
        //skip this test if ImgBBApiKey doesn't set as environment variable
        if (ImgBBApiKey == null) {
            Assertions.assertDoesNotThrow(() -> {
                Image image = imageManager.uploadAndInsertImage(TEST_IMAGE_BASE64, TEST_USER_ID_1, true);
                System.out.printf("Image link for human verify (expired after 10 min): %s", image.getLarge());
            });
            // When testing locally, this could fail when you didn't set up environment variable ImgBBApiKey
            // To solve this: Go to Heroku to find the value of ImgBBApiKey and set it locally
        }

    }

    @Test
    public void GIVEN_emptyUsername_WHEN_uploadAndInsertImage_THEN_throwException() {
        Assertions.assertThrows(InvalidParameterException.class, () ->
                imageManager.uploadAndInsertImage(TEST_IMAGE_BASE64, ""));
    }

    @Test
    public void GIVEN_validImage_WHEN_insertImage_THEN_insertIntoDatabase() {
        imageManager.insertImage(TEST_IMAGE_2);
        Assertions.assertEquals(TEST_IMAGE_2, imageManager.getImage(TEST_IMAGE_IMAGE_ID_2));
    }

    @Test
    public void GIVEN_existImageId_WHEN_insertImage_THEN_throwException() {
        Assertions.assertThrows(InvalidParameterException.class, () -> imageManager.insertImage(TEST_IMAGE_1));
    }

    @Test
    public void GIVEN_existImageId_WHEN_getThumbUrl_THEN_returnUrl() {
        Assertions.assertEquals(TEST_IMAGE_THUMB_1, imageManager.getThumbUrl(TEST_IMAGE_IMAGE_ID_1));
    }

    @Test
    public void GIVEN_invalidImageId_WHEN_getThumbUrl_THEN_throwException() {
        Assertions.assertThrows(InvalidParameterException.class, () -> imageManager.getThumbUrl(TEST_IMAGE_IMAGE_ID_2));
    }

    @Test
    public void GIVEN_existImageId_WHEN_getMediumUrl_THEN_returnUrl() {
        Assertions.assertEquals(TEST_IMAGE_MEDIUM_1, imageManager.getMediumUrl(TEST_IMAGE_IMAGE_ID_1));
    }

    @Test
    public void GIVEN_invalidImageId_WHEN_getMediumUrl_THEN_throwException() {
        Assertions.assertThrows(InvalidParameterException.class, () -> imageManager.getMediumUrl(TEST_IMAGE_IMAGE_ID_2));
    }

    @Test
    public void GIVEN_existImageId_WHEN_getLargeUrl_THEN_returnUrl() {
        Assertions.assertEquals(TEST_IMAGE_LARGE_1, imageManager.getLargeUrl(TEST_IMAGE_IMAGE_ID_1));
    }

    @Test
    public void GIVEN_invalidImageId_WHEN_getLargeUrl_THEN_throwException() {
        Assertions.assertThrows(InvalidParameterException.class, () -> imageManager.getLargeUrl(TEST_IMAGE_IMAGE_ID_2));
    }
}

