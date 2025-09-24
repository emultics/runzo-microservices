package com.runzo.common.crypto;
import com.runzo.common.crypto.factory.CryptoFactory;
import com.runzo.common.crypto.strategy.AesCryptoStrategy;
import com.runzo.common.crypto.strategy.CryptoStrategy;
import com.runzo.common.crypto.strategy.CryptoType;
public class CryptoProcessor implements CryptoService{
    private final CryptoStrategy strategy;

    public CryptoProcessor(CryptoType type, String secretKey) {
        this.strategy = CryptoFactory.getStrategy(type, secretKey);
    }

    @Override
    public String encrypt(String plainText) {
        return strategy.encrypt(plainText);
    }

    @Override
    public String decrypt(String cipherText) {
        if (strategy instanceof AesCryptoStrategy){
            return ((AesCryptoStrategy) strategy).decrypt(cipherText);
        }

        throw new UnsupportedOperationException("Decrypt not supported for this strategy");
    }

    @Override
    public boolean verify(String plainText, String encryptedText) {
        return strategy.verify(plainText, encryptedText);
    }
}