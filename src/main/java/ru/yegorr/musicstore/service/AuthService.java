package ru.yegorr.musicstore.service;

import ru.yegorr.musicstore.dto.UserLoginDto;
import ru.yegorr.musicstore.dto.UserRegistrationDto;
import ru.yegorr.musicstore.exception.ApplicationException;

public interface AuthService {
    void register(UserRegistrationDto userRegistration) throws ApplicationException;

    String login(UserLoginDto userLogin) throws ApplicationException;
}
