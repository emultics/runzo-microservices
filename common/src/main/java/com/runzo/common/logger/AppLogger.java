package com.runzo.common.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

public class AppLogger {
    private static volatile AppLogger instance;

    private Logger logger;
    private AppLogger(Class<?> clazz){
        logger = LoggerFactory.getLogger(clazz);
    }

    public static AppLogger getInstance(Class<?> clazz){
        if(instance==null){
            synchronized (AppLogger.class){
                if(instance==null){
                    instance = new AppLogger(clazz);
                }
            }
        }

        return instance;
    }

    public void info(String message){
        logger.info(message);
    }

    public void info(String message, Object o){
        logger.info(message, o);
    }
    public void info(String message, Object... objects){
        logger.error(message, objects);
    }

    public void info(String message, Marker m){
        logger.info(m, message);
    }

    public void error(String message, Throwable t){
        logger.error(message, t);
    }
    public void error(String message){
        logger.error(message);
    }
}
