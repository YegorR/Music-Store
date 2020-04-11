package ru.yegorr.musicstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yegorr.musicstore.dto.UserLoginDto;
import ru.yegorr.musicstore.dto.UserRegistrationDto;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public void register(UserRegistrationDto userRegistration) throws ApplicationException {
        if (userRepository.countAllByEmail(userRegistration.getEmail()) != 0) {
            throw new ApplicationException();
        }
        if (userRepository.countAllByNickname(userRegistration.getNickname()) != 0) {
            throw new ApplicationException();
        }

    }

    @Override
    public String login(UserLoginDto userLogin) throws ApplicationException {
        return "";
    }
}
