package com.group2.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group2.inventory.repository.AuthRepository;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    // Business logic: Login
    public String login(String username, String password) {
        // Validation
        if (!"admin".equals(username)) {
            return "Invalid username!";
        }

        if (!"1234".equals(password)) {
            return "Invalid password!";
        }


        System.out.println("Login successful! Welcome, " + username);
        return "Login successful! Welcome, " + username;
    }
}

