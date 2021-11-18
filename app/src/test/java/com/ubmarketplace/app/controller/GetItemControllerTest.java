package com.ubmarketplace.app.controller;

import com.ubmarketplace.app.dto.GetItemResponse;
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

import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_IMAGE_ID_1;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_IMAGE_ID_2;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_THUMB_1;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_THUMB_2;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_5;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_CATEGORY_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_DESCRIPTION_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_ID_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_ID_5;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_MEETING_PLACE_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_NAME_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_PHONE_NUMBER_FORMATTED_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_PRICE_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_USER_ID_4;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetItemControllerTest {
    GetItemController getItemController;

    @Mock
    ImageManager imageManager;
    @Mock
    ItemManager itemManager;
    @Mock
    UserManager userManager;

    @BeforeAll
    public void setup() {
        Mockito.when(itemManager.getItemById(any())).thenReturn(null);
        Mockito.when(itemManager.getItemById(eq(TEST_ITEM_ID_4))).thenReturn(TEST_ITEM_4);
        Mockito.when(itemManager.getItemById(eq(TEST_ITEM_ID_5))).thenReturn(TEST_ITEM_5);
        Mockito.when(imageManager.getLargeUrl(anyString())).thenReturn("Test Failed");
        Mockito.when(imageManager.getLargeUrl(eq(TEST_IMAGE_IMAGE_ID_1))).thenReturn(TEST_IMAGE_THUMB_1);
        Mockito.when(imageManager.getLargeUrl(eq(TEST_IMAGE_IMAGE_ID_2))).thenReturn(TEST_IMAGE_THUMB_2);
        getItemController = new GetItemController(imageManager, itemManager, userManager);
    }

    @Test
    public void GIVEN_validItemID_WHEN_getItem_THEN_returnItem() {
        GetItemResponse response = getItemController.getItem(TEST_ITEM_ID_4);

        ResponseItem item = response.getItem();
        Assertions.assertEquals(TEST_ITEM_ID_4, item.getItemId());
        Assertions.assertEquals(TEST_ITEM_NAME_4, item.getName());
        Assertions.assertEquals(TEST_ITEM_USER_ID_4, item.getOwner().getUserId());
        Assertions.assertEquals(TEST_ITEM_CATEGORY_4, item.getCategory());
        Assertions.assertEquals(TEST_ITEM_DESCRIPTION_4, item.getDescription());
        Assertions.assertEquals(TEST_ITEM_PRICE_4, item.getPrice());
        Assertions.assertEquals(TEST_IMAGE_THUMB_1, item.getImages().get(0));
        Assertions.assertEquals(TEST_IMAGE_THUMB_2, item.getImages().get(1));
        Assertions.assertEquals(TEST_ITEM_MEETING_PLACE_4, item.getMeetingPlace());
        Assertions.assertEquals(TEST_ITEM_PHONE_NUMBER_FORMATTED_4, item.getContactPhoneNumber());
    }
}
