package ru.yegorr.musicstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yegorr.musicstore.dto.UserRegistrationDto;

@RestController
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<?> register(UserRegistrationDto userRegistration) {
        return ResponseEntity.status(200).body("Not working");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login() {
        return ResponseEntity.status(200).body("Not working");
    }
}
