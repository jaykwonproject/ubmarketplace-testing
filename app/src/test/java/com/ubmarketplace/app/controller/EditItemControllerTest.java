package com.ubmarketplace.app.controller;

import com.ubmarketplace.app.dto.EditItemRequest;
import com.ubmarketplace.app.manager.ItemManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static com.ubmarketplace.app.TestStatic.TEST_ITEM_CATEGORY_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_DESCRIPTION_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_ID_1;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_IMAGE_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_MEETING_PLACE_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_NAME_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_PHONE_NUMBER_FORMATTED_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_PRICE_4;
import static com.ubmarketplace.app.TestStatic.TEST_USER_ID_1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class EditItemControllerTest {
    EditItemController editItemController;

    @Mock
    ItemManager itemManager;

    @BeforeAll
    public void setup() {
        itemManager = Mockito.mock(ItemManager.class);
        Mockito.
        doNothing().when(itemManager).editItem(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyDouble(),
                any(),
                anyString(),
                anyString(),
                anyString(),
                any(),
                any());

        editItemController = new EditItemController(null, itemManager, null);
    }

    @Test
    public void GIVEN_goodInput_WHEN_editItem_THEN_returnNothing() {
        Assertions.assertDoesNotThrow(() -> editItemController.editItem(EditItemRequest.builder()
                        .userId(TEST_USER_ID_1)
                        .item(EditItemRequest.EditItemRequestItem.builder()
                                .itemId(TEST_ITEM_ID_1)
                                .name(TEST_ITEM_NAME_4)
                                .category(TEST_ITEM_CATEGORY_4)
                                .description(TEST_ITEM_DESCRIPTION_4)
                                .price(TEST_ITEM_PRICE_4)
                                .images(TEST_ITEM_IMAGE_4)
                                .meetingPlace(TEST_ITEM_MEETING_PLACE_4)
                                .contactPhoneNumber(TEST_ITEM_PHONE_NUMBER_FORMATTED_4)
                                .build())
                .build()));
    }

}
