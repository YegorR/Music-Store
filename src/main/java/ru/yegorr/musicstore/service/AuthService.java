package ru.yegorr.musicstore.service;

import ru.yegorr.musicstore.controller.ActualUserInformation;
import ru.yegorr.musicstore.dto.request.LoginDto;
import ru.yegorr.musicstore.dto.response.LoginResponseDto;
import ru.yegorr.musicstore.dto.request.RegistrationDto;
import ru.yegorr.musicstore.exception.ClientException;

public interface AuthService {
    void register(RegistrationDto userRegistration) throws ClientException;

    LoginResponseDto login(LoginDto userLogin) throws ClientException;

    ActualUserInformation check(String token) throws ClientException;
}
