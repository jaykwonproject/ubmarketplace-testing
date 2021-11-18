package com.ubmarketplace.app.controller;

import com.ubmarketplace.app.dto.ItemDeleteRequest;
import com.ubmarketplace.app.model.Item;
import com.ubmarketplace.app.model.User;
import com.ubmarketplace.app.repository.ItemRepository;
import com.ubmarketplace.app.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.security.InvalidParameterException;

import static com.ubmarketplace.app.TestStatic.TEST_ITEM_ID_1;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_ID_2;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_NAME_1;
import static com.ubmarketplace.app.TestStatic.TEST_ITEM_NAME_2;
import static com.ubmarketplace.app.TestStatic.TEST_PASSWORD_3;
import static com.ubmarketplace.app.TestStatic.TEST_USER_ID_3;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class ItemDeleteControllerTest {
    @Autowired
    ItemDeleteController itemDeleteController;



    @BeforeAll
    static void setup(@Autowired ItemRepository itemRepository, @Autowired UserRepository userRepository) {
        userRepository.insert(User.builder().userId(TEST_USER_ID_3).password(TEST_PASSWORD_3).build());
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_1).name(TEST_ITEM_NAME_1).build());
        itemRepository.insert(Item.builder().itemId(TEST_ITEM_ID_2).name(TEST_ITEM_NAME_2).build());
    }

    @Test
    void DeleteItemByID_THEN_return_True() {
        String response = itemDeleteController.delete(new ItemDeleteRequest(TEST_ITEM_ID_1));
        Assertions.assertEquals(response, "success");
    }

    @Test
    void DeleteItemByWrongID_THEN_return_False() {
        Assertions.assertThrows(InvalidParameterException.class, () -> itemDeleteController.delete(new ItemDeleteRequest("123")));
    }




}