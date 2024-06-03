package com.example.demo.service.impl;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserOrder submit(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            log.warn("Cannot submit order. Error 404: user not found");
            return null;
        }
        UserOrder order = UserOrder.createFromCart(user.get().getCart());
        orderRepository.save(order);
        log.info("Order successfully submitted");
        return order;
    }

    @Transactional
    @Override
    public List<UserOrder> getOrdersForUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            log.warn("Cannot retrieve orders for user. Error 404: user not found");
            return null;
        }
        log.info("Order history retrieved for user");
        return orderRepository.findByUser(user.get());
    }
}
