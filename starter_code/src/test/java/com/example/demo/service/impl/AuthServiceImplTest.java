package com.example.demo.service.impl;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.service.AuthService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class AuthServiceImplTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createUser_userNotExisted_correctPassword_successfullyCreate() {
        createUser("authAdmin1", "randomPassword1", "randomPassword1");
        Optional<User> user = userRepository.findByUsername("authAdmin1");

        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals("authAdmin1", user.get().getUsername());
    }

    @ParameterizedTest()
    @CsvSource(value = {
            "authAdmin3,random3,random3",
            "authAdmin4,randomPassword4,random4"
    })
    void createUser_userNotExists_incorrectPasswordFormat_unsuccessfullyCreate(String username, String password, String confirmPassword) {
        User user = createUser(username, password, confirmPassword);
        Assertions.assertNull(user);
        userRepository.deleteAll();
    }

    @Test
    void createUser_UserExists_unsuccessfullyCreate() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("authAdmin2");
        request.setPassword("randomPassword2");
        request.setConfirmPassword("randomPassword2");
        authService.createUser(request);

        User user = authService.createUser(request);
        Assertions.assertNull(user);
        userRepository.deleteAll();
    }

    private User createUser(String username, String password, String confirmPassword) {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setConfirmPassword(confirmPassword);
        return authService.createUser(request);
    }
}