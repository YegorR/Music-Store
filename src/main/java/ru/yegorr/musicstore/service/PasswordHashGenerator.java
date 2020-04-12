package ru.yegorr.musicstore.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.LongPasswordStrategies;


import java.security.SecureRandom;

public class PasswordHashGenerator {
    private final SecureRandom randomizer = new SecureRandom();
    public byte[] generateRandomSalt() {
        byte[] salt = new byte[16];
        randomizer.nextBytes(salt);
        return salt;
    }

    public byte[] generateHash(byte[] password, byte[] salt) {
        return BCrypt.with(LongPasswordStrategies.hashSha512(BCrypt.Version.VERSION_2A)).hash(12, salt, password);
    }
}
