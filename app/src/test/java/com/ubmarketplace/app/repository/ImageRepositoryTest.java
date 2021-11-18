package com.ubmarketplace.app.repository;

import com.ubmarketplace.app.model.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_1;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_2;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_IMAGE_ID_1;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_IMAGE_ID_2;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ImageRepositoryTest {
    @Autowired
    ImageRepository imageRepository;

    @Test
    void GIVEN_validItem_WHEN_insert_THEN_insertIntoDatabase() {
        // If it doesn't fail, see as a success
        Assertions.assertDoesNotThrow(() -> imageRepository.insert(TEST_IMAGE_1));
    }

    @Test
    void GIVEN_validItem_WHEN_remove_THEN_removeFromDatabase() {
        Assertions.assertDoesNotThrow(() -> imageRepository.insert(TEST_IMAGE_1));
        // If it doesn't fail, see as a success
        Assertions.assertDoesNotThrow(() -> imageRepository.remove(TEST_IMAGE_1));
    }

    @Test
    void GIVEN_itemsInDatabase_WHEN_findAll_THEN_findAllItems() {
        imageRepository.insert(TEST_IMAGE_1);
        imageRepository.insert(TEST_IMAGE_2);

        List<String> validImageId = new ArrayList<String>(){{
            add(TEST_IMAGE_IMAGE_ID_1);
            add(TEST_IMAGE_IMAGE_ID_2);
        }};

        List<Image> result = imageRepository.findAll();
        for(Image image : result){
            Assertions.assertTrue(validImageId.contains(image.getImageId()));
        }
    }

    @Test
    void GIVEN_itemsInDatabase_WHEN_findById_THEN_returnTheItem() {
        imageRepository.insert(TEST_IMAGE_1);
        imageRepository.insert(TEST_IMAGE_2);
        Assertions.assertEquals(TEST_IMAGE_1, imageRepository.findById(TEST_IMAGE_IMAGE_ID_1));
    }
}
