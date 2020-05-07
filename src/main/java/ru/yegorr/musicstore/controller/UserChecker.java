package ru.yegorr.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.exception.ForbiddenException;
import ru.yegorr.musicstore.service.AuthService;

@Component
public class UserChecker {
    private final AuthService authService;

    @Autowired
    public UserChecker(AuthService authService) {
        this.authService = authService;
    }

    public boolean isAdmin(String token) throws ApplicationException {
        ActualUserInformation userInformation = authService.check(token);
        if (!userInformation.getAdmin()) {
            throw new ForbiddenException("You have no rights to do this");
        }
        return userInformation.getAdmin();
    }

    public Long getUserIdOrThrow(String token) throws ApplicationException {
        ActualUserInformation userInformation = authService.check(token);
        return userInformation.getUserId();
    }
}
