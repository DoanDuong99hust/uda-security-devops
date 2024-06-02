package com.example.demo.service.impl;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User userResponse;

    @BeforeEach
    void prepare() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("userAdmin");
        request.setPassword("randomPassword");
        request.setConfirmPassword("randomPassword");
        userResponse = authService.createUser(request);
    }

    @AfterEach
    void clear() {
        userRepository.deleteAll();
    }

    @Test
    void findById_userExists_User() {
        User user = userService.findById(userResponse.getId());
        Assertions.assertNotNull(user);
        Assertions.assertEquals("userAdmin", user.getUsername());
    }

    @Test
    void findById_userNotExist_null() {
        User user = userService.findById(userResponse.getId()+1);
        Assertions.assertNull(user);
    }

    @Test
    void findByUsername_userExists_User() {
        User user = userService.findByUserName("userAdmin");
        Assertions.assertEquals(userResponse.getUsername(), user.getUsername());
        Assertions.assertEquals(userResponse.getPassword(), user.getPassword());
    }

    @Test
    void findByUsername_userNotExists_null() {
        User user = userService.findByUserName("userAdmin1");
        Assertions.assertNull(user);
    }
}