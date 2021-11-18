package com.ubmarketplace.app.controller;

import com.ubmarketplace.app.dto.NewItemRequest;
import com.ubmarketplace.app.dto.NewItemResponse;
import com.ubmarketplace.app.model.Item;
import com.ubmarketplace.app.model.User;
import com.ubmarketplace.app.repository.ImageRepository;
import com.ubmarketplace.app.repository.ItemRepository;
import com.ubmarketplace.app.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_1;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_2;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_CATEGORY_3;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_DESCRIPTION_3;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_IMAGE_3;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_MEETING_PLACE_3;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_NAME_3;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_PHONE_NUMBER_FORMATTED_3;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_PHONE_NUMBER_UNFORMATTED_3;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_PRICE_3;
import static com.ubmarketplace.app.TestStatic.TEST_PASSWORD_3;
import static com.ubmarketplace.app.TestStatic.TEST_USER_DISPLAY_NAME_3;
import static com.ubmarketplace.app.TestStatic.TEST_USER_ID_3;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class NewItemControllerTest {
    @Autowired
    NewItemController newItemController;

    @BeforeAll
    static void setup(@Autowired UserRepository userRepository, @Autowired ImageRepository imageRepository) {
        userRepository.insert(User.builder().userId(TEST_USER_ID_3).password(TEST_PASSWORD_3).displayName(TEST_USER_DISPLAY_NAME_3).build());
        imageRepository.insert(TEST_IMAGE_1);
        imageRepository.insert(TEST_IMAGE_2);
    }

    @Test
    public void GIVEN_goodInput_WHEN_mewItem_THEN_returnCorrectNewItemResponse(@Autowired ItemRepository itemRepository) {
        Item item = Item.builder().
                name(TEST_ITEM_NAME_3).
                userId(TEST_USER_ID_3).
                category(TEST_ITEM_CATEGORY_3).
                description(TEST_ITEM_DESCRIPTION_3).
                price(TEST_ITEM_PRICE_3).
                images(TEST_ITEM_IMAGE_3).
                meetingPlace(TEST_ITEM_MEETING_PLACE_3).build();

        NewItemResponse response = newItemController.newItem(
                new NewItemRequest(
                        TEST_ITEM_NAME_3,
                        TEST_USER_ID_3,
                        TEST_ITEM_CATEGORY_3,
                        TEST_ITEM_DESCRIPTION_3,
                        TEST_ITEM_PRICE_3,
                        TEST_ITEM_IMAGE_3,
                        TEST_ITEM_MEETING_PLACE_3,
                        TEST_ITEM_PHONE_NUMBER_UNFORMATTED_3));

        Item testItem = itemRepository.findById(response.getItem().getItemId());

        Assertions.assertEquals(TEST_ITEM_NAME_3, testItem.getName());
        Assertions.assertEquals(TEST_USER_ID_3, testItem.getUserId());
        Assertions.assertEquals(TEST_ITEM_DESCRIPTION_3, testItem.getDescription());
        Assertions.assertEquals(TEST_ITEM_PRICE_3, testItem.getPrice());
        Assertions.assertEquals(TEST_ITEM_IMAGE_3, testItem.getImages());
        Assertions.assertEquals(TEST_ITEM_MEETING_PLACE_3, testItem.getMeetingPlace());
        Assertions.assertEquals(TEST_ITEM_PHONE_NUMBER_FORMATTED_3, testItem.getContactPhoneNumber());
    }
}
