package com.runzo.common.config;

import com.runzo.common.logger.AppLogger;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigManager {
    private static final ConfigManager INSTANCE = new ConfigManager();
    private final Properties properties = new Properties();

    private final AppLogger appLogger = AppLogger.getInstance(ConfigManager.class);

    public static ConfigManager getInstance(){
        return INSTANCE;
    }

    private ConfigManager(){
        loadProperties();
    }


    private void loadProperties(){
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")){
            if(inputStream==null){
                throw new RuntimeException("application.properties file not found!");
            }
            properties.load(inputStream);
            appLogger.info("✅ Properties loaded successfully");

        }catch (Exception ex){
            throw  new RuntimeException("Failed to load application.properties");
        }
    }

    public String getProperty(String key){
        String value = properties.getProperty(key);
        if(value!=null && value.startsWith("${") && value.endsWith("}")){
            return resolveEnvPlaceHolder(value);
        }
        return value;
    }

    private String resolveEnvPlaceHolder(String value){
        if(value==null) return null;

        // it matches ${..}
        Pattern pattern = Pattern.compile("\\$\\{([^}]+)}");
        Matcher matcher = pattern.matcher(value);

        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()){
            String placeHolder = matcher.group(1);

            String[] parts = placeHolder.split(":", 2);
            String key = parts[0].trim();
            String defaultValue = parts.length>1 ? parts[1].trim(): null;

            String resolved = System.getenv(key);
            // 2. If not found in env, try system properties (-DDB_HOST=value in JVM)
            if(resolved==null || resolved.isEmpty()){
                resolved = System.getProperty(key);
            }
            // 3. If still not found, use default value
            if(resolved==null || (resolved.isEmpty() && defaultValue!=null)){
                resolved = defaultValue;
                // Example: If DB_HOST not set anywhere → resolved="localhost"
            }

            if(resolved==null){
                resolved = "";
            }

            matcher.appendReplacement(stringBuffer, Matcher.quoteReplacement(resolved));
            // Example: "${DB_HOST:localhost}" → "prod-db.com"


        }

        matcher.appendTail(stringBuffer);
        // Append remaining part of string after last match
        // Example: final "jdbc:mysql://prod-db.com:3306/mydb"
        return stringBuffer.toString();
    }
}
