package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ru.yegorr.musicstore.exception.ClientException;
import ru.yegorr.musicstore.service.AuthService;

@Component
public class UserChecker {
    private final AuthService authService;

    @Autowired
    public UserChecker(AuthService authService) {
        this.authService = authService;
    }

    public void checkAdmin(String token) throws ClientException {
        ActualUserInformation userInformation = authService.check(token);
        if (!userInformation.getAdmin()) {
            throw new ClientException(HttpStatus.FORBIDDEN);
        }
    }

    public Long getUserIdOrThrow(String token) throws ClientException {
        ActualUserInformation userInformation = authService.check(token);
        return userInformation.getUserId();
    }
}
