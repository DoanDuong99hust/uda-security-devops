package com.example.demo.service;

import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;

public interface AuthService {
    User createUser(CreateUserRequest createUserRequest);
}
