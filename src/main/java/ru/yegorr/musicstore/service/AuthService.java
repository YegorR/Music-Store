package ru.yegorr.musicstore.service;

import ru.yegorr.musicstore.dto.UserLoginDto;
import ru.yegorr.musicstore.dto.UserRegistrationDto;

public interface AuthService {
    void register(UserRegistrationDto userRegistration);

    String login(UserLoginDto userLogin);
}
