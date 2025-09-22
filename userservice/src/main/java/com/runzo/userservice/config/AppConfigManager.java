package com.runzo.userservice.config;

import com.runzo.common.config.ConfigManager;
import com.runzo.userservice.constant.UserConstant;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class AppConfigManager {
    private ConfigManager configManager;

    @PostConstruct
    public void init(){
        configManager = ConfigManager.getInstance();

    }

    public String getSecretKey(){
        String key = configManager.getProperty(UserConstant.SECRET_KEY_PROPERTY);
        if(key==null){
            throw new IllegalArgumentException("SECRET_KEY not found in env, please set and try again");
        }
        return key;
    }


}
