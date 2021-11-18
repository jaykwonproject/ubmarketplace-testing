package com.ubmarketplace.app.controller.image;

import com.ubmarketplace.app.dto.image.UploadRequest;
import com.ubmarketplace.app.dto.image.UploadResponse;
import com.ubmarketplace.app.manager.ImageManager;
import com.ubmarketplace.app.model.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_1;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_BASE64;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_LARGE_1;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_MEDIUM_1;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_THUMB_1;
import static com.ubmarketplace.app.TestStatic.TEST_USER_ID_1;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UploadControllerTest {
    UploadController uploadController;

    @Mock
    ImageManager imageManager;

    @BeforeAll
    public void setup() {
        Mockito.when(imageManager.uploadAndInsertImage(anyString(), anyString())).thenAnswer(
                (Answer<Image>) invocation -> {
                    Image image = TEST_IMAGE_1;
                    return Image.builder()
                            .imageId(image.getImageId())
                            .thumb(image.getThumb())
                            .medium(image.getMedium())
                            .large(image.getLarge())
                            .uploadBy(invocation.getArgument(1))
                            .uploadTime(image.getUploadTime())
                            .build();
                }
        );
        uploadController = new UploadController(imageManager);
    }

    @Test
    public void GIVEN_goodInput_WHEN_upload_THEN_returnCorrectUploadResponse() {
        UploadResponse response = uploadController.upload(UploadRequest.builder()
                        .userId(TEST_USER_ID_1)
                        .image(TEST_IMAGE_BASE64)
                        .build());
        Assertions.assertEquals(TEST_USER_ID_1, response.getImage().getUploadBy());
        Assertions.assertEquals(TEST_IMAGE_LARGE_1, response.getImage().getLarge());
        Assertions.assertEquals(TEST_IMAGE_MEDIUM_1, response.getImage().getMedium());
        Assertions.assertEquals(TEST_IMAGE_THUMB_1, response.getImage().getThumb());
    }
}
