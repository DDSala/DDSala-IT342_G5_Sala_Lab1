package com.sala.userauth.controller;

import com.sala.userauth.model.User;
import com.sala.userauth.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository repo) {
        this.userRepository = repo;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        java.util.List<User> users = userRepository.findAll();
        
        if (!users.isEmpty()) {
            return ResponseEntity.ok(users.get(0));
        } else {
            return ResponseEntity.status(404).body("User profile not found in database");
        }
    }
}