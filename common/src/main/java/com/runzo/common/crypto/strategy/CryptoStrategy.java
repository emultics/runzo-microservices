package com.runzo.common.crypto.strategy;

public interface CryptoStrategy {
    String encrypt(String input);
    boolean verify(String raw, String encrypted);

}
