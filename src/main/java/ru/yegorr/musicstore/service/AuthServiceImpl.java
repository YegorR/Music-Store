package ru.yegorr.musicstore.service;

import org.springframework.stereotype.Service;
import ru.yegorr.musicstore.dto.UserLoginDto;
import ru.yegorr.musicstore.dto.UserRegistrationDto;
import ru.yegorr.musicstore.exception.ApplicationException;

@Service
public class AuthServiceImpl implements AuthService{
    @Override
    public void register(UserRegistrationDto userRegistration) throws ApplicationException {
    }

    @Override
    public String login(UserLoginDto userLogin) throws ApplicationException {
        return "";
    }
}
