package com.example.demo.service.impl;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.service.AuthService;
import com.example.demo.service.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class CartServiceImplTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private CartService cartService;

    @BeforeEach
    void prepare() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("cardAdmin");
        request.setPassword("randomPassword");
        request.setConfirmPassword("randomPassword");
        authService.createUser(request);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "cardAdmin2, 1, 10",
            "cardAdmin, 3 , 10"
    })
    void addToCart_userAndItemNotExist_null(String username, int itemId, int quantity) {
        ModifyCartRequest request = getRequest(username, itemId, quantity);
        Cart cart = cartService.addToCart(request);
        Assertions.assertNull(cart);
    }

    @Test
    void addToCart_userAndItemExists_cartData() {
        ModifyCartRequest request = getRequest("cardAdmin", 1, 10);
        Cart cart = cartService.addToCart(request);
        Assertions.assertEquals(10, cart.getItems().size());
        Assertions.assertEquals(new BigDecimal("29.90"), cart.getTotal());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "cardAdmin2, 1, 10",
            "cardAdmin, 3 , 10"
    })
    void removeToCart_userAndItemNotExist_null(String username, int itemId, int quantity) {
        ModifyCartRequest request = getRequest(username, itemId, quantity);
        Cart cart = cartService.removeFromCart(request);
        Assertions.assertNull(cart);
    }

    @Test
    void removeToCart_userAndItemExists_cartData() {
        ModifyCartRequest request = getRequest("cardAdmin", 1, 10);
        Cart cart = cartService.removeFromCart(request);
        Assertions.assertEquals(0, cart.getItems().size());
        Assertions.assertEquals(new BigDecimal("0.00"), cart.getTotal());
    }

    private ModifyCartRequest getRequest(String username, int itemId, int quantity) {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername(username);
        request.setItemId(itemId);
        request.setQuantity(quantity);
        return request;
    }
}