package ru.yegorr.musicstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yegorr.musicstore.dto.LoginDto;
import ru.yegorr.musicstore.dto.UserRegistrationDto;
import ru.yegorr.musicstore.entity.UserEntity;
import ru.yegorr.musicstore.exception.ApplicationException;
import ru.yegorr.musicstore.exception.UserIsAlreadyExistsException;
import ru.yegorr.musicstore.exception.WrongLoginOrPasswordException;
import ru.yegorr.musicstore.repository.UserRepository;

import java.util.Arrays;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    private final PasswordHashGenerator hashGenerator = new PasswordHashGenerator();

    private final TokenHandler tokenHandler = new TokenHandler();

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(UserRegistrationDto userRegistration) throws ApplicationException {
        if (userRepository.countAllByEmail(userRegistration.getEmail()) != 0) {
            throw new UserIsAlreadyExistsException("User with this email already exists");
        }
        if (userRepository.countAllByNickname(userRegistration.getNickname()) != 0) {
            throw new UserIsAlreadyExistsException("User with this nickname already exists");
        }

        byte[] salt = hashGenerator.generateRandomSalt();
        byte[] hashPassword = hashGenerator.generateHash(userRegistration.getPassword(), salt);

        UserEntity userEntity = new UserEntity();
        userEntity.setAdmin(userRepository.count() == 0);   // if an user is the first one then he will be an admin
        userEntity.setNickname(userRegistration.getNickname());
        userEntity.setEmail(userRegistration.getEmail());
        userEntity.setSalt(salt);
        userEntity.setPassword(hashPassword);
        userRepository.save(userEntity);
    }

    @Override
    public String login(LoginDto userLogin) throws ApplicationException {
        UserEntity user = userRepository.findByEmail(userLogin.getEmail());
        if (user == null) {
            throw new WrongLoginOrPasswordException("Wrong login or password");
        }

        byte[] hashPassword = hashGenerator.generateHash(userLogin.getPassword(), user.getSalt());
        if (!Arrays.equals(hashPassword, user.getPassword())) {
            throw new WrongLoginOrPasswordException("Wrong login or password");
        }
        return tokenHandler.getToken(user.getUserId());
    }
}
