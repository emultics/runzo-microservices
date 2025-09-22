package com.runzo.common.exceptions.ex;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
