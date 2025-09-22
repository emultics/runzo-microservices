package com.runzo.userservice.controller;

import com.runzo.common.logger.AppLogger;
import com.runzo.common.response.ApiResponse;
import com.runzo.userservice.dto.RegisterRequest;
import com.runzo.userservice.service.UserHandlerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final AppLogger appLogger = AppLogger.getInstance(UserController.class);
    @Autowired
    UserHandlerService service;

    @PostMapping
    public ResponseEntity<?> doRegister(@Valid @RequestBody RegisterRequest request){
        ApiResponse<?> response = service.userRegistration(request);
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(response.getErrorCode()).body(response);
    }



}
