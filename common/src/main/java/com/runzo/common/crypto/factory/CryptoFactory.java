package com.runzo.common.crypto.factory;

import com.runzo.common.crypto.strategy.AesCryptoStrategy;
import com.runzo.common.crypto.strategy.BcryptStrategy;
import com.runzo.common.crypto.strategy.CryptoStrategy;
import com.runzo.common.crypto.strategy.CryptoType;

public class CryptoFactory {
    public static CryptoStrategy getStrategy(CryptoType cryptoType, String secretKey){
        switch (cryptoType){
            case BCRYPT -> {
                return new BcryptStrategy();
            }
            case AES -> {
                return new AesCryptoStrategy(secretKey);
            }
            case null, default ->
                throw new IllegalArgumentException("UnknownCryptoType: "+cryptoType);
        }
    }
}
