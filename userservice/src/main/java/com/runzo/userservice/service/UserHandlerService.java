package com.runzo.userservice.service;

import com.runzo.common.exceptions.ex.UserNotFoundException;
import com.runzo.common.response.ApiResponse;
import com.runzo.userservice.dto.RegisterRequest;
import com.runzo.userservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public ApiResponse<?> getProfileById(String id){
        User user = service.getUserById(id);
        return ApiResponse.success(user, "User profile fetched");
    }

    public ApiResponse<?> getProfileByEmail(String email){
        User user = service.getProfileByEmail(email);
        return ApiResponse.success(user, "User profile fetched");
    }

    public ApiResponse<?> getProfileByPhone(String phone){
        if(phone == null || phone.isBlank()){
            throw new RuntimeException("Phone is required!");
        }
        User user = service.getUserByPhone(phone);
        return ApiResponse.success(user, "User profile fetched");
    }

    public ApiResponse<Boolean> isUserExist(String userId){
        if(!service.isUserExist(userId)){
            throw new UserNotFoundException("User not Found!");
        }
        return ApiResponse.success(true, "User found!");
    }





}
