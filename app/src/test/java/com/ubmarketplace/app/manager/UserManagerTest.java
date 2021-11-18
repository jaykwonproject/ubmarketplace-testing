package com.ubmarketplace.app.manager;

import com.ubmarketplace.app.model.User;
import com.ubmarketplace.app.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.security.InvalidParameterException;

import static com.ubmarketplace.app.Static.USER_ROLE_ADMIN;
import static com.ubmarketplace.app.Static.USER_ROLE_USER;
import static com.ubmarketplace.app.TestStatic.TEST_ALWAYS_WRONG_PASSWORD;
import static com.ubmarketplace.app.TestStatic.TEST_PASSWORD_1;
import static com.ubmarketplace.app.TestStatic.TEST_PASSWORD_2;
import static com.ubmarketplace.app.TestStatic.TEST_PASSWORD_3;
import static com.ubmarketplace.app.TestStatic.TEST_USER_ID_1;
import static com.ubmarketplace.app.TestStatic.TEST_USER_ID_2;
import static com.ubmarketplace.app.TestStatic.TEST_USER_ID_3;
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserManagerTest {
    @Autowired
    UserManager usermanager;

    @BeforeAll
    static void setup(@Autowired UserRepository userRepository) {
        userRepository.insert(User.builder().userId(TEST_USER_ID_1).role(USER_ROLE_USER).password(TEST_PASSWORD_1).displayName("displayName").build());
        userRepository.insert(User.builder().userId(TEST_USER_ID_2).role(USER_ROLE_ADMIN).password(TEST_PASSWORD_2).displayName("displayName").build());
    }

    @Test
    public void GIVEN_goodInput_WHEN_addNewUser_THEN_returnTrue(@Autowired UserRepository userRepository){
        User user = User.builder().userId(TEST_USER_ID_3).password(TEST_PASSWORD_3).build();
        usermanager.addNewUser(TEST_USER_ID_3, TEST_PASSWORD_3, "DisplayName");
        Assertions.assertEquals(userRepository.findById(TEST_USER_ID_3).getUserId(), user.getUserId());
    }
    @Test
    public void GIVEN_goodInput_WHEN_getUserRole_THEN_return_appropriate_user_Role(@Autowired UserRepository userRepository){
        Assertions.assertEquals(userRepository.findById(TEST_USER_ID_1).getRole(), usermanager.getUserRole(TEST_USER_ID_1));
        Assertions.assertEquals(userRepository.findById(TEST_USER_ID_2).getRole(), usermanager.getUserRole(TEST_USER_ID_2));
    }

    @Test
    public void GIVEN_goodInput_WHEN_loginVerification_THEN_returnTrue() {
        Assertions.assertTrue(usermanager.loginVerification(TEST_USER_ID_1, TEST_PASSWORD_1));
    }

    @Test
    public void GIVEN_goodInputWrongPassword_WHEN_loginVerification_THEN_returnFalse() {
        Assertions.assertFalse(usermanager.loginVerification(TEST_USER_ID_2, TEST_ALWAYS_WRONG_PASSWORD));
    }

    @Test
    public void GIVEN_emptyUsername_WHEN_loginVerification_THEN_returnFalse() {
        Assertions.assertThrows(InvalidParameterException.class, () -> usermanager.loginVerification("", TEST_PASSWORD_1));
    }

    @Test
    public void GIVEN_emptyPassword_WHEN_loginVerification_THEN_returnFalse() {
        Assertions.assertThrows(InvalidParameterException.class, () -> usermanager.loginVerification(TEST_USER_ID_1, ""));
    }
}
