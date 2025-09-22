package com.runzo.userservice.service;

import com.runzo.userservice.dto.RegisterRequest;
import com.runzo.userservice.entity.User;
import org.springframework.stereotype.Service;


interface UserService {
    public User doRegister(RegisterRequest request);

}
