package ru.yegorr.musicstore.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class TokenHandler {
    private static final String KEY = "MY_SECRET_KEY";

    private static final Algorithm algorithm = Algorithm.HMAC256(KEY);

    public String getToken(Long userId) {
        return JWT.create().withClaim("id", userId).sign(algorithm);
    }
}
