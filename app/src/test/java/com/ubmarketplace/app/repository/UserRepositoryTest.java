package com.ubmarketplace.app.repository;

import com.ubmarketplace.app.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static com.ubmarketplace.app.TestStatic.TEST_PASSWORD_1;
import static com.ubmarketplace.app.TestStatic.TEST_PASSWORD_2;
import static com.ubmarketplace.app.TestStatic.TEST_USER_ID_1;
import static com.ubmarketplace.app.TestStatic.TEST_USER_ID_2;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    void GIVEN_validUser_WHEN_insert_THEN_insertIntoDatabase() {
        // If it doesn't fail, see as a success
        Assertions.assertDoesNotThrow(() -> userRepository.insert(User.builder()
                .userId(TEST_USER_ID_1)
                .password(TEST_PASSWORD_1)
                .build()));
    }

    @Test
    void GIVEN_validUser_WHEN_remove_THEN_removeFromDatabase() {
        userRepository.insert(User.builder()
                .userId(TEST_USER_ID_1)
                .password(TEST_PASSWORD_1)
                .build());
        // If it doesn't fail, see as a success
        Assertions.assertDoesNotThrow(() -> userRepository.remove(User.builder()
                .userId(TEST_USER_ID_1)
                .build()));
    }

    @Test
    void GIVEN_usersInDatabase_WHEN_findAll_THEN_findAllItems() {
        userRepository.insert(User.builder()
                .userId(TEST_USER_ID_1)
                .password(TEST_PASSWORD_1)
                .build());
        userRepository.insert(User.builder()
                .userId(TEST_USER_ID_2)
                .password(TEST_PASSWORD_2)
                .build());

        List<String> validItemId = new ArrayList<String>(){{
            add(TEST_USER_ID_1);
            add(TEST_USER_ID_2);
        }};

        List<User> result = userRepository.findAll();
        for(User user : result){
            Assertions.assertTrue(validItemId.contains(user.getUserId()));
        }
    }

    @Test
    void GIVEN_usersInDatabase_WHEN_findById_THEN_returnTheItem() {
        User user1 = User.builder()
                .userId(TEST_USER_ID_1)
                .password(TEST_PASSWORD_1)
                .build();
        userRepository.insert(user1);
        userRepository.insert(User.builder()
                .userId(TEST_USER_ID_2)
                .password(TEST_PASSWORD_2)
                .build());

        Assertions.assertEquals(user1, userRepository.findById(TEST_USER_ID_1));
    }
}
