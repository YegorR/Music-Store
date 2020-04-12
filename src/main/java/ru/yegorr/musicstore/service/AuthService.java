package ru.yegorr.musicstore.service;

import ru.yegorr.musicstore.dto.LoginDto;
import ru.yegorr.musicstore.dto.UserRegistrationDto;
import ru.yegorr.musicstore.exception.ApplicationException;

public interface AuthService {
    void register(UserRegistrationDto userRegistration) throws ApplicationException;

    String login(LoginDto userLogin) throws ApplicationException;
}
