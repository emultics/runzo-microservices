package com.runzo.userservice.service;

import com.runzo.common.response.ApiResponse;
import com.runzo.userservice.dto.RegisterRequest;
import com.runzo.userservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserHandlerService {
    @Autowired
    UserService service;

    public ApiResponse<?> userRegistration(RegisterRequest request){
        User user = service.doRegister(request);
        if(user==null){
            throw new RuntimeException("User creation failed!");
        }

        return ApiResponse.success(user, "User created!");
    }


}
