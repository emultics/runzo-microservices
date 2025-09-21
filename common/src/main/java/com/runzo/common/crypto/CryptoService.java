package com.runzo.common.crypto;

public interface CryptoService {
    String encrypt(String plainText);
    String decrypt(String cipherText);   // for AES only
    boolean verify(String plainText, String encryptedText);
}
