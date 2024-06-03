package com.example.demo.service.impl;

import com.example.demo.jwt.CustomUserDetails;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.UpperCase;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findById_userExists_User() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("userAdmin");
        request.setPassword("randomPassword");
        request.setConfirmPassword("randomPassword");

        User user = authService.createUser(request);
        Assertions.assertNotNull(user);
        Assertions.assertEquals("userAdmin", user.getUsername());
    }

    @Test
    void findById_userNotExist_null() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("userAdmin");
        request.setPassword("randomPassword");
        request.setConfirmPassword("randomPassword");
        authService.createUser(request);
        User user = userService.findById(100L);
        Assertions.assertNull(user);
    }

    @Test
    void findByUsername_userExists_User() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("userAdmin");
        request.setPassword("randomPassword");
        request.setConfirmPassword("randomPassword");
        authService.createUser(request);
        User user = userService.findByUserName("userAdmin");
        Assertions.assertNotNull(user);
    }

    @Test
    void findByUsername_userNotExists_null() {
        User user = userService.findByUserName("userAdmin1");
        Assertions.assertNull(user);
    }

    private User manualCreateUser(Long userId, String username, String password) {
        User user = new User();
        user.setId(userId);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}