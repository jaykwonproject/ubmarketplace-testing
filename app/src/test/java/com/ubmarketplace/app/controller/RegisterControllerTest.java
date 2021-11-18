package com.ubmarketplace.app.controller;

import com.ubmarketplace.app.dto.RegisterRequest;
import com.ubmarketplace.app.dto.RegisterResponse;
import com.ubmarketplace.app.manager.UserManager;
import com.ubmarketplace.app.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static com.ubmarketplace.app.TestStatic.TEST_PASSWORD_1;
import static com.ubmarketplace.app.TestStatic.TEST_USER_ID_1;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegisterControllerTest {
    RegisterController registerController;

    @Mock
    UserManager userManager;

    @BeforeAll
    public void setup() {
        Mockito.when(userManager.addNewUser(anyString(), anyString(), anyString())).thenAnswer(
                (Answer<User>) invocation -> User.builder()
                        .userId(invocation.getArgument(0))
                        .password(invocation.getArgument(1))
                        .displayName(invocation.getArgument(2))
                        .build()
        );
        registerController = new RegisterController(userManager);
    }

    @Test
    public void GIVEN_goodInput_WHEN_register_THEN_returnCorrectRegisterResponse() {
        RegisterResponse response = registerController.register(RegisterRequest.builder()
                .userId(TEST_USER_ID_1)
                .password(TEST_PASSWORD_1)
                .displayName("Displayname")
                .build());
        Assertions.assertEquals(TEST_USER_ID_1, response.getUser().getUserId());
    }
}
