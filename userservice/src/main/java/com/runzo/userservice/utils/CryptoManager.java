package com.runzo.userservice.utils;

import com.runzo.common.crypto.CryptoProcessor;
import com.runzo.common.crypto.CryptoService;
import com.runzo.common.crypto.strategy.CryptoType;
import com.runzo.userservice.config.AppConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CryptoManager {
    private final AppConfigManager configManager;
    private final CryptoService service;

    public CryptoManager(AppConfigManager configManager){
        this.configManager = configManager;
        this.service = new CryptoProcessor(CryptoType.BCRYPT, configManager.getSecretKey());
    }


    public String encrypt(String rawText){
        return service.encrypt(rawText);
    }

    public String decrypt(String encrypted){
        return service.decrypt(encrypted);
    }
    public boolean verify(String raw, String encrypted){
        return service.verify(raw, encrypted);
    }

}
