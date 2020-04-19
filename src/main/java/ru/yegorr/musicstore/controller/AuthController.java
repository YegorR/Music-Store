package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yegorr.musicstore.dto.LoginResponseDto;
import ru.yegorr.musicstore.dto.response.ResponseBuilder;
import ru.yegorr.musicstore.dto.LoginDto;
import ru.yegorr.musicstore.dto.RegistrationDto;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.service.AuthService;

@RestController
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> register(@RequestBody RegistrationDto userRegistration) throws ApplicationException {
        authService.register(userRegistration);

        return ResponseBuilder.getBuilder().code(200).body("OK").getResponseEntity();
    }

    @PostMapping(value = "/login",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> login(@RequestBody LoginDto userLogin) throws ApplicationException {
        LoginResponseDto loginResponse = authService.login(userLogin);

        return ResponseBuilder.getBuilder().code(200).body(loginResponse).getResponseEntity();
    }
}
