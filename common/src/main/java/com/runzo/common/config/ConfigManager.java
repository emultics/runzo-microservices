package com.runzo.common.config;

import com.runzo.common.logger.AppLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ConfigManager {

    private final Environment environment;

    private final AppLogger appLogger = AppLogger.getInstance(ConfigManager.class);

    public ConfigManager(Environment environment) {
        this.environment = environment;
    }

    public String getProperty(String key){
        String value = environment.getProperty(key);
        if(value==null){
            throw new IllegalArgumentException("Property '" + key + "' not found in config server or environment");
        }

        return value;
    }
}
