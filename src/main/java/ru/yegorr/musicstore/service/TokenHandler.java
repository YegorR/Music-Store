package ru.yegorr.musicstore.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenHandler {
    private static final String KEY = "MY_SECRET_KEY";

    private static final Algorithm algorithm = Algorithm.HMAC256(KEY);

    private static final JWTVerifier verifier = JWT.require(algorithm).build();

    public String getToken(Long userId) {
        return JWT.create().withClaim("id", userId).sign(algorithm);
    }

    public Long checkToken(String token) {
        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim("id").asLong();
        } catch (JWTVerificationException ex) {
            return null;
        }
    }
}
