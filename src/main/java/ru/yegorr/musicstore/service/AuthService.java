package ru.yegorr.musicstore.service;

import ru.yegorr.musicstore.dto.request.LoginDto;
import ru.yegorr.musicstore.dto.request.RegistrationDto;
import ru.yegorr.musicstore.dto.response.SuccessfulLoginDto;
import ru.yegorr.musicstore.exception.ClientException;
import ru.yegorr.musicstore.security.ActualUserInformation;

public interface AuthService {
  void register(RegistrationDto userRegistration) throws ClientException;

  SuccessfulLoginDto login(LoginDto userLogin) throws ClientException;

  ActualUserInformation check(String token) throws ClientException;
}
