package com.sala.userauth.controller;

import com.sala.userauth.model.User;
import com.sala.userauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") 
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String email = body.get("email");
        String password = body.get("password");

        if (userRepository.existsByUsername(username)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken");
        }

        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is already registered");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");

        return userRepository.findByEmail(email)
                .map(user -> {
                    // Inside the login method:
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        Map<String, Object> response = new HashMap<>();
                        response.put("token", "dummy-jwt-token-12345");
                        response.put("username", user.getUsername());
                        response.put("email", user.getEmail()); // ADD THIS LINE so Android gets the email
                        return ResponseEntity.ok(response);
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
                    }
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist"));
    }
}