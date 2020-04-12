package ru.yegorr.musicstore.service;

import ru.yegorr.musicstore.dto.LoginDto;
import ru.yegorr.musicstore.dto.LoginResponseDto;
import ru.yegorr.musicstore.dto.RegistrationDto;
import ru.yegorr.musicstore.exception.ApplicationException;

public interface AuthService {
    void register(RegistrationDto userRegistration) throws ApplicationException;

    LoginResponseDto login(LoginDto userLogin) throws ApplicationException;
}
