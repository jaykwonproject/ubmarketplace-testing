package com.ubmarketplace.app.manager;

import com.ubmarketplace.app.model.Item;
import com.ubmarketplace.app.model.User;
import com.ubmarketplace.app.repository.ImageRepository;
import com.ubmarketplace.app.repository.ItemRepository;
import com.ubmarketplace.app.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

import static com.ubmarketplace.app.Static.USER_ROLE_ADMIN;
import static com.ubmarketplace.app.Static.USER_ROLE_USER;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_1;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_2;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_IMAGE_ID_1;
import static com.ubmarketplace.app.TestStatic.TEST_IMAGE_IMAGE_ID_2;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_5;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_CATEGORY_3;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_CATEGORY_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_DESCRIPTION_3;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_DESCRIPTION_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_ID_1;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_ID_2;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_ID_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_ID_5;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_IMAGE_3;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_IMAGE_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_MEETING_PLACE_3;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_MEETING_PLACE_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_NAME_1;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_NAME_2;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_NAME_3;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_NAME_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_PHONE_NUMBER_FORMATTED_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_PRICE_3;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_PRICE_4;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_USER_ID_4;
import static com.ubmarketplace.app.TestStatic.TEST_PASSWORD_3;
import static com.ubmarketplace.app.TestStatic.TEST_USER_ID_1;
import static com.ubmarketplace.app.TestStatic.TEST_USER_ID_2;
import static com.ubmarketplace.app.TestStatic.TEST_USER_ID_3;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ItemManagerTest {
    @Autowired
    ItemManager itemManager;

    @BeforeEach
    void setup(@Autowired ItemRepository itemRepository, @Autowired UserRepository userRepository,
               @Autowired ImageRepository imageRepository) {
        userRepository.insert(User.builder().userId(TEST_USER_ID_1).role(USER_ROLE_USER).build());
        userRepository.insert(User.builder().userId(TEST_USER_ID_2).role(USER_ROLE_ADMIN).build());
        userRepository.insert(User.builder().userId(TEST_USER_ID_3).password(TEST_PASSWORD_3).build());
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_1).name(TEST_ITEM_NAME_1).build());
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_2).name(TEST_ITEM_NAME_2).build());
        itemRepository.insert(TEST_ITEM_4);
        itemRepository.insert(TEST_ITEM_5);
        imageRepository.insert(TEST_IMAGE_1);
        imageRepository.insert(TEST_IMAGE_2);
    }

    @Test
    public void GIVEN_goodInput_When_addNewItem_Then_returnTrue(@Autowired ItemRepository itemRepository, @Autowired ImageManager imageManager) {
        Item item = itemManager.addItem(
                TEST_ITEM_NAME_3,
                TEST_USER_ID_3,
                TEST_ITEM_CATEGORY_3,
                TEST_ITEM_DESCRIPTION_3,
                TEST_ITEM_PRICE_3,
                TEST_ITEM_IMAGE_3,
                TEST_ITEM_MEETING_PLACE_3,
                "",
                imageManager
        );
        Assertions.assertEquals(itemRepository.findById(item.getItemId()), item);

        //Remove added item in order to avoid of interrupting other test.
        itemRepository.remove(itemRepository.findById(item.getItemId()));
    }

    @Test
    public void GIVEN_goodInput_WHEN_loginVerification_THEN_returnTrue() {
        List<String> validItemId = Arrays.asList(TEST_ITEM_ID_1, TEST_ITEM_ID_2, TEST_ITEM_ID_4,
                TEST_ITEM_ID_5);

        List<Item> result = itemManager.getAllItem();
        System.out.println(result);
        for (Item item : result) {
            Assertions.assertTrue(validItemId.contains(item.getItemId()));
        }
    }

    @Test
    public void Remove_Item_THEN_returnTrue(@Autowired ItemRepository itemRepository) {
        Assertions.assertTrue(itemManager.deleteItem(TEST_ITEM_ID_1));
    }

    @Test
    public void Remove_ItemTwice_THEN_returnTrue(@Autowired ItemRepository itemRepository) {
        Assertions.assertEquals(itemManager.deleteItem(TEST_ITEM_ID_1), true);
        Assertions.assertThrows(InvalidParameterException.class, () -> itemManager.deleteItem(TEST_ITEM_ID_1));
    }

    @Test
    public void GIVEN_validItemId_WHEN_getItemById_THEN_returnItem() {
        Item item = itemManager.getItemById(TEST_ITEM_ID_4);
        Assertions.assertEquals(TEST_ITEM_ID_4, item.getItemId());
        Assertions.assertEquals(TEST_ITEM_NAME_4, item.getName());
        Assertions.assertEquals(TEST_ITEM_USER_ID_4, item.getUserId());
        Assertions.assertEquals(TEST_ITEM_CATEGORY_4, item.getCategory());
        Assertions.assertEquals(TEST_ITEM_DESCRIPTION_4, item.getDescription());
        Assertions.assertEquals(TEST_ITEM_PRICE_4, item.getPrice());
        Assertions.assertEquals(TEST_IMAGE_IMAGE_ID_1, item.getImages().get(0));
        Assertions.assertEquals(TEST_IMAGE_IMAGE_ID_2, item.getImages().get(1));
        Assertions.assertEquals(TEST_ITEM_MEETING_PLACE_4, item.getMeetingPlace());
        Assertions.assertEquals(TEST_ITEM_PHONE_NUMBER_FORMATTED_4, item.getContactPhoneNumber());
    }

    @Test
    public void GIVEN_inValidItemId_WHEN_getItemById_THEN_throwException() {
        Assertions.assertThrows(InvalidParameterException.class,
                () -> itemManager.getItemById(""));

        Assertions.assertThrows(InvalidParameterException.class,
                () -> itemManager.getItemById("NotValidItemID"));
    }

    @Test
    public void GIVEN_validInput_WHEN_editItem_THEN_editGivenItem(@Autowired UserManager userManager,
                                                                  @Autowired ImageManager imageManager) {
        itemManager.editItem(TEST_ITEM_ID_4, "new name", TEST_ITEM_CATEGORY_4, TEST_ITEM_DESCRIPTION_4,
                TEST_ITEM_PRICE_4, TEST_ITEM_IMAGE_4, TEST_ITEM_MEETING_PLACE_4, TEST_ITEM_PHONE_NUMBER_FORMATTED_4,
                TEST_USER_ID_1, userManager, imageManager);

        Item afterChange = itemManager.getItemById(TEST_ITEM_ID_4);
        Assertions.assertEquals("new name", afterChange.getName());
    }

    @Test
    public void GIVEN_validInputEditAllValue_WHEN_editItem_THEN_editGivenItem(@Autowired UserManager userManager,
                                                                              @Autowired ImageManager imageManager) {
        itemManager.editItem(TEST_ITEM_ID_4, "new name", "new category", "new description",
                102.35, Arrays.asList(TEST_IMAGE_IMAGE_ID_2, TEST_IMAGE_IMAGE_ID_1),
                "new meeting place", "(716) 365-9876",
                TEST_USER_ID_1, userManager, imageManager);

        Item afterChange = itemManager.getItemById(TEST_ITEM_ID_4);
        Assertions.assertEquals("new name", afterChange.getName());
        Assertions.assertEquals("new category", afterChange.getCategory());
        Assertions.assertEquals("new description", afterChange.getDescription());
        Assertions.assertEquals(102.35, afterChange.getPrice());
        Assertions.assertEquals("new meeting place", afterChange.getMeetingPlace());
        Assertions.assertEquals("(716) 365-9876", afterChange.getContactPhoneNumber());
        Assertions.assertEquals(2, afterChange.getImages().size());
        Assertions.assertEquals(TEST_IMAGE_IMAGE_ID_2, afterChange.getImages().get(0));
        Assertions.assertEquals(TEST_IMAGE_IMAGE_ID_1, afterChange.getImages().get(1));
    }

    @Test
    public void GIVEN_validInputFromAdmin_WHEN_editItem_THEN_editGivenItem(@Autowired UserManager userManager,
                                                                           @Autowired ImageManager imageManager) {
        itemManager.editItem(TEST_ITEM_ID_4, "new name by Admin", TEST_ITEM_CATEGORY_4, TEST_ITEM_DESCRIPTION_4,
                TEST_ITEM_PRICE_4, TEST_ITEM_IMAGE_4, TEST_ITEM_MEETING_PLACE_4, TEST_ITEM_PHONE_NUMBER_FORMATTED_4,
                TEST_USER_ID_2, userManager, imageManager);

        Item afterChange = itemManager.getItemById(TEST_ITEM_ID_4);
        Assertions.assertEquals("new name by Admin", afterChange.getName());
    }

    @Test
    public void GIVEN_notOwnerEditItem_WHEN_editItem_THEN_throwException(@Autowired UserManager userManager,
                                                                         @Autowired ImageManager imageManager) {
        Assertions.assertThrows(InvalidParameterException.class, () -> itemManager.editItem(TEST_ITEM_ID_4,
                "new name without permission", TEST_ITEM_CATEGORY_4, TEST_ITEM_DESCRIPTION_4,
                TEST_ITEM_PRICE_4, TEST_ITEM_IMAGE_4, TEST_ITEM_MEETING_PLACE_4, TEST_ITEM_PHONE_NUMBER_FORMATTED_4,
                TEST_USER_ID_3, userManager, imageManager));
    }

    @Test
    public void GIVEN_EmptyItemId_WHEN_editItem_THEN_throwException(@Autowired UserManager userManager,
                                                                    @Autowired ImageManager imageManager) {
        Assertions.assertThrows(InvalidParameterException.class, () -> itemManager.editItem("",
                "new name", TEST_ITEM_CATEGORY_4, TEST_ITEM_DESCRIPTION_4,
                TEST_ITEM_PRICE_4, TEST_ITEM_IMAGE_4, TEST_ITEM_MEETING_PLACE_4, TEST_ITEM_PHONE_NUMBER_FORMATTED_4,
                TEST_USER_ID_3, userManager, imageManager));
    }

    @Test
    public void GIVEN_EmptyUserID_WHEN_editItem_THEN_throwException(@Autowired UserManager userManager,
                                                                    @Autowired ImageManager imageManager) {
        Assertions.assertThrows(InvalidParameterException.class, () -> itemManager.editItem(TEST_ITEM_ID_4,
                "new name", TEST_ITEM_CATEGORY_4, TEST_ITEM_DESCRIPTION_4,
                TEST_ITEM_PRICE_4, TEST_ITEM_IMAGE_4, TEST_ITEM_MEETING_PLACE_4, TEST_ITEM_PHONE_NUMBER_FORMATTED_4,
                "", userManager, imageManager));
    }

    @Test
    public void GIVEN_InvalidImageId_WHEN_editItem_THEN_throwException(@Autowired UserManager userManager,
                                                                       @Autowired ImageManager imageManager) {
        Assertions.assertThrows(InvalidParameterException.class, () -> itemManager.editItem(TEST_ITEM_ID_4,
                TEST_ITEM_NAME_4, TEST_ITEM_CATEGORY_4, TEST_ITEM_DESCRIPTION_4,
                TEST_ITEM_PRICE_4, Arrays.asList(TEST_IMAGE_IMAGE_ID_2, "InvalidImageId"),
                TEST_ITEM_MEETING_PLACE_4, TEST_ITEM_PHONE_NUMBER_FORMATTED_4, TEST_USER_ID_1,
                userManager, imageManager));
    }

}
