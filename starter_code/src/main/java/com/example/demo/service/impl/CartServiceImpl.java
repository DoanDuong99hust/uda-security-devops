package com.example.demo.service.impl;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public CartServiceImpl(UserRepository userRepository, ItemRepository itemRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    @Transactional
    @Override
    public Cart addToCart(ModifyCartRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if(user.isEmpty()) {
            return null;
        }
        Optional<Item> item = itemRepository.findById(request.getItemId());
        if(item.isEmpty()) {
            return null;
        }
        Cart cart = user.get().getCart();
        IntStream.range(0, request.getQuantity())
                .forEach(i -> cart.addItem(item.get()));
        cartRepository.save(cart);
        return cart;
    }

    @Transactional
    @Override
    public Cart removeFromCart(ModifyCartRequest request) {
        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if(user.isEmpty()) {
            return null;
        }
        Optional<Item> item = itemRepository.findById(request.getItemId());
        if(item.isEmpty()) {
            return null;
        }
        Cart cart = user.get().getCart();
        IntStream.range(0, request.getQuantity())
                .forEach(i -> cart.removeItem(item.get()));
        cartRepository.save(cart);
        return cart;
    }
}
