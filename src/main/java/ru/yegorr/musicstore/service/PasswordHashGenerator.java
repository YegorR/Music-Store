package ru.yegorr.musicstore.service;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.security.SecureRandom;

public class PasswordHashGenerator {
    private final SecureRandom randomizer = new SecureRandom();
    public byte[] generateRandomSalt() {
        byte[] salt = new byte[64];
        randomizer.nextBytes(salt);
        return salt;
    }

    public byte[] generateHash(byte[] password, byte[] salt) {
        byte[] passwordWithSalt = new byte[password.length + salt.length];
        System.arraycopy(password, 0, passwordWithSalt, 0, password.length);
        System.arraycopy(salt, 0, passwordWithSalt, password.length, salt.length);

        return BCrypt.withDefaults().hash(12, passwordWithSalt);
    }
}
