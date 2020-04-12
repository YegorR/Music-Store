package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yegorr.musicstore.dto.ResponseDto;
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

    @PostMapping(value = "/register",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> register(@RequestBody UserRegistrationDto userRegistration) throws ApplicationException {
        authService.register(userRegistration);
        ResponseDto response = new ResponseDto();
        response.setCode(200);
        response.setBody("OK");
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping(value = "/login",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLogin) throws ApplicationException {
        String token = authService.login(userLogin);
        ResponseDto response = new ResponseDto();
        response.setCode(200);
        response.setBody(token);
        return ResponseEntity.status(200).body(response);
    }
}
