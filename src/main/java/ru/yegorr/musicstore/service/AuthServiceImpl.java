package ru.yegorr.musicstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yegorr.musicstore.security.ActualUserInformation;
import ru.yegorr.musicstore.dto.request.LoginDto;
import ru.yegorr.musicstore.dto.response.SuccessfulLoginDto;
import ru.yegorr.musicstore.dto.request.RegistrationDto;
import ru.yegorr.musicstore.entity.UserEntity;
import ru.yegorr.musicstore.exception.*;
import ru.yegorr.musicstore.repository.UserRepository;
import ru.yegorr.musicstore.security.PasswordHashGenerator;
import ru.yegorr.musicstore.security.TokenHandler;

import java.util.Arrays;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordHashGenerator hashGenerator = new PasswordHashGenerator();

    private final TokenHandler tokenHandler = new TokenHandler();

    @Autowired
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void register(RegistrationDto userRegistration) throws ClientException {
        if (userRepository.countAllByEmail(userRegistration.getEmail()) != 0) {
            throw new ClientException(HttpStatus.FORBIDDEN, "User with this email already exist");
        }
        if (userRepository.countAllByNickname(userRegistration.getNickname()) != 0) {
            throw new ClientException(HttpStatus.FORBIDDEN, "User with this nickname already exist");
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
    @Transactional
    public SuccessfulLoginDto login(LoginDto userLogin) throws ClientException {
        UserEntity user = userRepository.findByEmail(userLogin.getEmail());
        if (user == null) {
            throw new ClientException(HttpStatus.UNAUTHORIZED, "Wrong login or password");
        }

        byte[] hashPassword = hashGenerator.generateHash(userLogin.getPassword(), user.getSalt());
        if (!Arrays.equals(hashPassword, user.getPassword())) {
            throw new ClientException(HttpStatus.UNAUTHORIZED, "Wrong login or password");
        }

        String token = tokenHandler.getToken(user.getUserId());

        SuccessfulLoginDto successfulLoginDto = new SuccessfulLoginDto();
        successfulLoginDto.setId(user.getUserId());
        successfulLoginDto.setNickname(user.getNickname());
        successfulLoginDto.setToken(token);
        successfulLoginDto.setAdmin(user.getAdmin());

        return successfulLoginDto;
    }

    @Override
    @Transactional
    public ActualUserInformation check(String token) throws ClientException {
        Long id = tokenHandler.checkToken(token);
        if (id == null) {
            throw new ClientException(HttpStatus.UNAUTHORIZED, "Wrong token");
        }

        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new ClientException(HttpStatus.UNAUTHORIZED, "Wrong token");
        }

        ActualUserInformation userInformation = new ActualUserInformation();
        userInformation.setUserId(id);
        userInformation.setAdmin(userOptional.get().getAdmin());

        return userInformation;
    }
}
