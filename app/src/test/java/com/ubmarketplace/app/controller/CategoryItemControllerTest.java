package com.ubmarketplace.app.controller;

import com.ubmarketplace.app.dto.CategoryItemRequest;
import com.ubmarketplace.app.dto.CategoryItemResponse;
import com.ubmarketplace.app.dto.ResponseItem;
import com.ubmarketplace.app.manager.ImageManager;
import com.ubmarketplace.app.manager.ItemManager;
import com.ubmarketplace.app.manager.UserManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;

import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_IMAGE_ID_1;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_IMAGE_ID_2;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_THUMB_1;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_THUMB_2;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_5;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_CATEGORY_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_CATEGORY_5;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_DESCRIPTION_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_DESCRIPTION_5;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_ID_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_ID_5;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_MEETING_PLACE_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_MEETING_PLACE_5;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_NAME_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_NAME_5;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_PHONE_NUMBER_FORMATTED_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_PHONE_NUMBER_FORMATTED_5;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_PRICE_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_PRICE_5;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_USER_ID_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_USER_ID_5;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoryItemControllerTest {
    CategoryItemController categoryItemController;

    @Mock
    ImageManager imageManager;
    @Mock
    ItemManager itemManager;
    @Mock
    UserManager userManager;

    @BeforeAll
    public void setup() {
        Mockito.when(itemManager.getCategoryItem("", "", "", "")).thenReturn(Arrays.asList(TEST_ITEM_4, TEST_ITEM_5));
        Mockito.when(imageManager.getThumbUrl(anyString())).thenReturn("Test Failed");
        Mockito.when(imageManager.getThumbUrl(eq(TEST_IMAGE_IMAGE_ID_1))).thenReturn(TEST_IMAGE_THUMB_1);
        Mockito.when(imageManager.getThumbUrl(eq(TEST_IMAGE_IMAGE_ID_2))).thenReturn(TEST_IMAGE_THUMB_2);
        categoryItemController = new CategoryItemController(imageManager, itemManager, userManager);
    }

    @Test
    public void GIVEN_goodInput_WHEN_allItem_THEN_returnItemList() {
        CategoryItemResponse response = categoryItemController.categorizeItem(new CategoryItemRequest("", "", "", ""));

        Assertions.assertEquals(2, response.getItem().size());

        ResponseItem item1 = response.getItem().get(0);
        Assertions.assertEquals(TEST_ITEM_ID_4, item1.getItemId());
        Assertions.assertEquals(TEST_ITEM_NAME_4, item1.getName());
        Assertions.assertEquals(TEST_ITEM_USER_ID_4, item1.getOwner().getUserId());
        Assertions.assertEquals(TEST_ITEM_CATEGORY_4, item1.getCategory());
        Assertions.assertEquals(TEST_ITEM_DESCRIPTION_4, item1.getDescription());
        Assertions.assertEquals(TEST_ITEM_PRICE_4, item1.getPrice());
        Assertions.assertEquals(TEST_IMAGE_THUMB_1, item1.getImages().get(0));
        Assertions.assertEquals(TEST_IMAGE_THUMB_2, item1.getImages().get(1));
        Assertions.assertEquals(TEST_ITEM_MEETING_PLACE_4, item1.getMeetingPlace());
        Assertions.assertEquals(TEST_ITEM_PHONE_NUMBER_FORMATTED_4, item1.getContactPhoneNumber());

        ResponseItem item2 = response.getItem().get(1);
        Assertions.assertEquals(TEST_ITEM_ID_5, item2.getItemId());
        Assertions.assertEquals(TEST_ITEM_NAME_5, item2.getName());
        Assertions.assertEquals(TEST_ITEM_USER_ID_5, item2.getOwner().getUserId());
        Assertions.assertEquals(TEST_ITEM_CATEGORY_5, item2.getCategory());
        Assertions.assertEquals(TEST_ITEM_DESCRIPTION_5, item2.getDescription());
        Assertions.assertEquals(TEST_ITEM_PRICE_5, item2.getPrice());
        Assertions.assertEquals(TEST_IMAGE_THUMB_2, item2.getImages().get(0));
        Assertions.assertEquals(TEST_ITEM_MEETING_PLACE_5, item2.getMeetingPlace());
        Assertions.assertEquals(TEST_ITEM_PHONE_NUMBER_FORMATTED_5, item2.getContactPhoneNumber());
    }
}
