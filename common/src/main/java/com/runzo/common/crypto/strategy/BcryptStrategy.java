package com.runzo.common.crypto.strategy;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class BcryptStrategy implements CryptoStrategy{
    private static final int workload = 12;


    @Override
    public String encrypt(String input) {
        return BCrypt.hashpw(input, BCrypt.gensalt(workload));
    }

    @Override
    public boolean verify(String raw, String encrypted) {
        return BCrypt.checkpw(raw, encrypted);
    }
}
