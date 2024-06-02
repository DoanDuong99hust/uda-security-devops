package com.example.demo.service;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.requests.ModifyCartRequest;

public interface CartService {
    Cart addToCart(ModifyCartRequest request);
    Cart removeFromCart(ModifyCartRequest request);
}
