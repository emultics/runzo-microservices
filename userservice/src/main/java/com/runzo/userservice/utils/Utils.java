package com.runzo.userservice.utils;

import com.runzo.userservice.dto.RegisterRequest;
import com.runzo.userservice.entity.User;

public class Utils {
    public static User mapToUser(RegisterRequest request){
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

    }
}
