package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yegorr.musicstore.dto.UserLoginDto;
import ru.yegorr.musicstore.dto.UserRegistrationDto;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.service.AuthService;

@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(UserRegistrationDto userRegistration) throws ApplicationException {
        authService.register(userRegistration);
        return ResponseEntity.status(200).body("Not working");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(UserLoginDto userLogin) throws ApplicationException {
        String token = authService.login(userLogin);
        return ResponseEntity.status(200).body(token);
    }
}