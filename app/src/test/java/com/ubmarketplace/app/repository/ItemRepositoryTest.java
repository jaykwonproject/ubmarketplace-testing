package com.ubmarketplace.app.repository;

import com.ubmarketplace.app.model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_IMAGE_ID_1;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_IMAGE_ID_2;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_5;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_CATEGORY_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_DESCRIPTION_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_ID_1;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_ID_2;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_ID_3;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_ID_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_IMAGE_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_MEETING_PLACE_3;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_MEETING_PLACE_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_NAME_1;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_NAME_2;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_NAME_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_PHONE_NUMBER_FORMATTED_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_PRICE_4;
import static com.ubmarketplace.app.TestStatic.TEST_USER_ID_1;
import static com.ubmarketplace.app.TestStatic.TEST_USER_ID_2;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    void GIVEN_validItem_WHEN_insert_THEN_insertIntoDatabase() {
        // If it doesn't fail, see as a success
        Assertions.assertDoesNotThrow(() -> itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_1)
                .name(TEST_ITEM_NAME_1).build()));
    }

    @Test
    void GIVEN_validItem_WHEN_remove_THEN_removeFromDatabase() {
        Assertions.assertDoesNotThrow(() -> itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_1)
                .name(TEST_ITEM_NAME_1).build()));
        // If it doesn't fail, see as a success
        Assertions.assertDoesNotThrow(() -> itemRepository.remove(Item.builder().itemId(TEST_ITEM_ID_1).build()));
    }

    @Test
    void GIVEN_itemsInDatabase_WHEN_findAll_THEN_findAllItems() {
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_1).name(TEST_ITEM_NAME_1).build());
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_2).name(TEST_ITEM_NAME_2).build());

        List<String> validItemId = new ArrayList<String>() {{
            add(TEST_ITEM_ID_1);
            add(TEST_ITEM_ID_2);
        }};

        List<Item> result = itemRepository.findAll();
        for (Item item : result) {
            Assertions.assertTrue(validItemId.contains(item.getItemId()));
        }
    }

    @Test
    void GIVEN_itemsInDatabase_WHEN_findById_THEN_returnTheItem() {
        Item item1 = Item.builder().itemId(TEST_ITEM_ID_1).name(TEST_ITEM_NAME_1).build();
        itemRepository.insert(item1);
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_2).name(TEST_ITEM_NAME_2).build());

        Assertions.assertEquals(item1, itemRepository.findById(TEST_ITEM_ID_1));
    }


    @Test
    void GIVEN_itemsInDatabase_WHEN_findcategorize_THEN_findAllcategorizeItems2() {
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_1).name(TEST_ITEM_NAME_1).category("Book").build());
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_2).name(TEST_ITEM_NAME_2).category("Car").build());

        List<String> validItemId = new ArrayList<String>() {{
            add(TEST_ITEM_ID_1);
        }};

        List<Item> result = itemRepository.getCategoryItem("Book", "", "", "");
        for (Item item : result) {
            Assertions.assertTrue(validItemId.contains(item.getItemId()));
        }
    }

    @Test
    void GIVEN_itemsInDatabase_WHEN_findcategorizeWITH_ID_THEN_findAllcategorizeItems() {
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_1).name(TEST_ITEM_NAME_1).userId(TEST_USER_ID_1).category("Book").price(23.0).build());
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_2).name(TEST_ITEM_NAME_1).userId(TEST_USER_ID_1).category("Car").price(1.0).build());
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_3).name(TEST_ITEM_NAME_1).userId(TEST_USER_ID_2).category("Car").price(40.0).build());

        List<String> validItemId = new ArrayList<String>() {{
            add(TEST_ITEM_ID_1);
            add(TEST_ITEM_ID_2);
        }};

        List<Item> result = itemRepository.getCategoryItem("", TEST_USER_ID_1, "", "");

        for (Item item : result) {
            Assertions.assertTrue(validItemId.contains(item.getItemId()));
        }
        Assertions.assertEquals(2, result.size());
    }


    @Test
    void GIVEN_itemsInDatabase_WHEN_findcategorizeWITH_Category_THEN_findAllcategorizeItems() {
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_1).name(TEST_ITEM_NAME_1).userId(TEST_USER_ID_1).category("Book").price(23.0).build());
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_2).name(TEST_ITEM_NAME_1).userId(TEST_USER_ID_1).category("Car").price(1.0).build());
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_3).name(TEST_ITEM_NAME_1).userId(TEST_USER_ID_2).category("Car").price(40.0).build());

        List<String> validItemId = new ArrayList<String>() {{
            add(TEST_ITEM_ID_2);
            add(TEST_ITEM_ID_3);
        }};

        List<Item> result = itemRepository.getCategoryItem("Car", "", "", "");

        for (Item item : result) {
            Assertions.assertTrue(validItemId.contains(item.getItemId()));
        }
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void GIVEN_itemsInDatabase_WHEN_findcategorizeWITH_Location_THEN_findAllcategorizeItems() {
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_1).name(TEST_ITEM_NAME_1).userId(TEST_USER_ID_1).category("Book").meetingPlace(TEST_ITEM_MEETING_PLACE_3).price(23.0).build());
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_2).name(TEST_ITEM_NAME_1).userId(TEST_USER_ID_1).category("Car").price(1.0).build());
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_3).name(TEST_ITEM_NAME_1).userId(TEST_USER_ID_2).category("Car").price(40.0).build());

        List<String> validItemId = new ArrayList<String>() {{
            add(TEST_ITEM_ID_1);

        }};

        List<Item> result = itemRepository.getCategoryItem("", "", TEST_ITEM_MEETING_PLACE_3, "");

        for (Item item : result) {
            Assertions.assertTrue(validItemId.contains(item.getItemId()));
        }
        Assertions.assertEquals(1, result.size());
    }


    @Test
    void GIVEN_itemsInDatabase_WHEN_findcategorize_THEN_findAllcategorizeItems() {
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_1).name(TEST_ITEM_NAME_1).userId(TEST_USER_ID_1).category("Book").price(23.0).build());
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_2).name(TEST_ITEM_NAME_1).userId(TEST_USER_ID_1).category("Car").price(1.0).build());
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_3).name(TEST_ITEM_NAME_1).userId(TEST_USER_ID_2).category("Car").price(40.0).build());

        List<String> validItemId = new ArrayList<String>() {{
            add(TEST_ITEM_ID_1);
            add(TEST_ITEM_ID_2);
            add(TEST_ITEM_ID_3);
        }};

        List<Item> result = itemRepository.getCategoryItem("", "", "", "");

        for (Item item : result) {
            Assertions.assertTrue(validItemId.contains(item.getItemId()));
        }
        Assertions.assertEquals(3, result.size());
    }

    @Test
    void GIVEN_itemsInDatabase_WHEN_findcategorize_WITH_PRICE_THEN_findAllcategorizeItems() {
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_1).name(TEST_ITEM_NAME_1).userId(TEST_USER_ID_1).category("Book").price(23.0).build());
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_2).name(TEST_ITEM_NAME_1).userId(TEST_USER_ID_1).category("Car").price(1.0).build());
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_3).name(TEST_ITEM_NAME_1).userId(TEST_USER_ID_2).category("Car").price(40.0).build());

        List<String> validItemId = new ArrayList<String>() {{
            add(TEST_ITEM_ID_1);
            add(TEST_ITEM_ID_2);
            add(TEST_ITEM_ID_3);
        }};

        List<Item> result = itemRepository.getCategoryItem("", "", "", "descend");

        double lastPrice = result.get(0).getPrice();
        for (Item item : result) {
            Assertions.assertTrue(validItemId.contains(item.getItemId()));
            Assertions.assertTrue(item.getPrice() <= lastPrice);
            lastPrice = item.getPrice();
        }
        Assertions.assertEquals(3, result.size());
    }

    @Test
    void GIVEN_validInput_WHEN_update_THEN_updateGivenItem() {
        itemRepository.insert(TEST_ITEM_4);
        itemRepository.insert(TEST_ITEM_5);

        itemRepository.update(TEST_ITEM_ID_4, "new name", TEST_ITEM_CATEGORY_4, TEST_ITEM_DESCRIPTION_4,
                TEST_ITEM_PRICE_4, TEST_ITEM_IMAGE_4, TEST_ITEM_MEETING_PLACE_4, TEST_ITEM_PHONE_NUMBER_FORMATTED_4);

        Item afterChange = itemRepository.findById(TEST_ITEM_ID_4);
        Assertions.assertEquals("new name", afterChange.getName());
    }

    @Test
    void GIVEN_validInputUpdateImage_WHEN_update_THEN_updateGivenItem() {
        itemRepository.insert(TEST_ITEM_4);
        itemRepository.insert(TEST_ITEM_5);

        itemRepository.update(TEST_ITEM_ID_4, TEST_ITEM_NAME_4, TEST_ITEM_CATEGORY_4, TEST_ITEM_DESCRIPTION_4,
                TEST_ITEM_PRICE_4, Arrays.asList(TEST_IMAGE_IMAGE_ID_2, TEST_IMAGE_IMAGE_ID_1),
                TEST_ITEM_MEETING_PLACE_4, TEST_ITEM_PHONE_NUMBER_FORMATTED_4);

        Item afterChange = itemRepository.findById(TEST_ITEM_ID_4);
        Assertions.assertEquals(2, afterChange.getImages().size());
        Assertions.assertEquals(TEST_IMAGE_IMAGE_ID_2, afterChange.getImages().get(0));
        Assertions.assertEquals(TEST_IMAGE_IMAGE_ID_1, afterChange.getImages().get(1));

        itemRepository.update(TEST_ITEM_ID_4, TEST_ITEM_NAME_4, TEST_ITEM_CATEGORY_4, TEST_ITEM_DESCRIPTION_4,
                TEST_ITEM_PRICE_4, Collections.singletonList(TEST_IMAGE_IMAGE_ID_2), TEST_ITEM_MEETING_PLACE_4,
                TEST_ITEM_PHONE_NUMBER_FORMATTED_4);

        afterChange = itemRepository.findById(TEST_ITEM_ID_4);
        Assertions.assertEquals(1, afterChange.getImages().size());
        Assertions.assertEquals(TEST_IMAGE_IMAGE_ID_2, afterChange.getImages().get(0));
    }

    @Test
    void GIVEN_invalidInput_WHEN_update_THEN_throwException() {
        itemRepository.insert(TEST_ITEM_4);
        itemRepository.insert(TEST_ITEM_5);

        Assertions.assertThrows(InvalidParameterException.class, () -> itemRepository.update("InvalidItemId",
                "new name", TEST_ITEM_CATEGORY_4, TEST_ITEM_DESCRIPTION_4, TEST_ITEM_PRICE_4, TEST_ITEM_IMAGE_4,
                TEST_ITEM_MEETING_PLACE_4, TEST_ITEM_PHONE_NUMBER_FORMATTED_4));

    }

}
