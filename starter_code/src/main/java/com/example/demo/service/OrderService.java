package com.example.demo.service;

import com.example.demo.model.persistence.UserOrder;

import java.util.List;

public interface OrderService {

    UserOrder submit(String username);
    List<UserOrder> getOrdersForUser(String username);
}
