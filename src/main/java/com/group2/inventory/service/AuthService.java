package com.group2.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group2.inventory.model.AuthEntity;
import com.group2.inventory.repository.AuthRepository;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    // Business logic: Login
    public String login(String username, String password) {
        // Validation
        if (username == null || username.trim().isEmpty()) {
            return "Username is required!";
        }

        if (password == null || password.trim().isEmpty()) {
            return "Password is required!";
        }

        // Business logic: Find user
        AuthEntity user = authRepository.findByUsername(username.toLowerCase());
        
        if (user == null) {
            return "User not found!";
        }

        // Business logic: Check password
        if (!user.getPassword().equals(password)) {
            return "Invalid password!";
        }

        return "Login successful! Welcome, " + user.getUsername();
    }
}

