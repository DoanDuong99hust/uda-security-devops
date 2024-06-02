package com.example.demo.service;

import com.example.demo.model.persistence.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    User findById(Long id);
    User findByUserName(String username);
    UserDetailsService userDetailsService();
}
