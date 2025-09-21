package com.runzo.common.crypto.strategy;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AesCryptoStrategy implements CryptoStrategy, DecryptHandler{
    private final String secretKey;

    public AesCryptoStrategy(String secretKey) {
        if(secretKey==null || secretKey.length()!=16){
            throw  new IllegalArgumentException("AES secretKey must be 16 characters (128-bit)");
        }
        this.secretKey = secretKey;
    }

    /**
     * Algorithm
     * plan text -> convert to bytes -> AES Encryption -> Encrypted bytes -> base64 encoding -> cipher text string
     * @param input
     * @return encrypted String
     */
    @Override
    public String encrypt(String input) {
        try{
            SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), CryptoType.AES.name());
            Cipher cipher = Cipher.getInstance(CryptoType.AES.name());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(input.getBytes()));
        }catch (Exception ex){
            throw new RuntimeException("AES Encryption failed", ex);
        }
    }

    @Override
    public boolean verify(String raw, String encrypted) {
        return raw.equals(decrypt(encrypted));
    }

    @Override
    public String decrypt(String encryptedText) {
        try{
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), CryptoType.AES.name());
            Cipher cipher = Cipher.getInstance(CryptoType.AES.name());
            cipher.init(Cipher.DECRYPT_MODE,keySpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));
        }catch (Exception ex){
            throw  new RuntimeException("AES Decryption failed", ex);
        }
    }
}
