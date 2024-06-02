package com.example.demo.service.impl;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.service.AuthService;
import com.example.demo.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceImplTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void submitOrder_userNotExist_null() {
        createUser("orderAdmin1", "randomPassword");
        UserOrder order = orderService.submit("orderAdmin2");
        Assertions.assertNull(order);
    }

    @Test
    void submitOrder_userExists_userOrder() {
        User userResponse = createUser("orderAdmin", "randomPassword");
        UserOrder order = orderService.submit("orderAdmin");
        Cart cart1 = userResponse.getCart();
        Assertions.assertEquals(cart1.getItems(), order.getItems());
        Assertions.assertEquals(cart1.getTotal().setScale(0, RoundingMode.HALF_UP), order.getTotal().setScale(0, RoundingMode.HALF_UP));
    }

    @Test
    void getOrdersForUser_userExists_listUserOrder() {
        createUser("orderAdmin2", "randomPassword");
        orderService.submit("orderAdmin2");
        List<UserOrder> userOrders = orderService.getOrdersForUser("orderAdmin2");
        Assertions.assertFalse(userOrders.isEmpty());
    }

    @Test
    void getOrdersForUser_userNotExist_emptyList() {
        createUser("orderAdmin3", "randomPassword");
        List<UserOrder> userOrders = orderService.getOrdersForUser("orderAdmin4");
        Assertions.assertNull(userOrders);
    }

    private User createUser(String userName, String password) {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(userName);
        request.setPassword(password);
        request.setConfirmPassword(password);
        User userResponse = authService.createUser(request);
        Cart cart = new Cart();
        Item item1 = getNewItem(1L, "Helmet", new BigDecimal("1.0"), "best buy");
        Item item2 = getNewItem(1L, "Monitor", new BigDecimal("1.0"), "best buy");
        cart.setItems(List.of(item1, item2));
        cart.setTotal(BigDecimal.TEN);
        cart.setUser(userResponse);
        userResponse.setCart(cart);
        userRepository.save(userResponse);
        return userResponse;
    }

    private Item getNewItem(Long id, String name, BigDecimal price, String description) {
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setPrice(price);
        item.setDescription(description);
        return item;
    }
}