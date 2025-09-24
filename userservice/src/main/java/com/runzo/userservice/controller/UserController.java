package com.runzo.userservice.controller;

import com.runzo.common.logger.AppLogger;
import com.runzo.common.response.ApiResponse;
import com.runzo.userservice.dto.RegisterRequest;
import com.runzo.userservice.service.UserHandlerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search")
    public ResponseEntity<?> getUser(
            @RequestParam(required = false,name = "id") String id,
            @RequestParam(required = false, name = "email") String email,
            @RequestParam(required = false, name = "phone") String phone
    ){
        if(id!=null){
            ApiResponse<?> response = service.getProfileById(id);
            return buildResponse(response);
        }

        if(phone!=null){
            ApiResponse<?> response = service.getProfileByPhone(phone);
           return buildResponse(response);
        }

        if(email!=null){
            ApiResponse<?> response = service.getProfileByEmail(email);
            return buildResponse(response);
        }


        return ResponseEntity.badRequest()
                .body(ApiResponse.error("Please provide either id, phone, or email", HttpStatus.BAD_REQUEST));
    }


    @GetMapping("{userId}/validate")
    public ResponseEntity<?> checkUserExistOrNot(@PathVariable("userId") String userId){
        ApiResponse<?> response = service.isUserExist(userId);
        return buildResponse(response);
    }


    private ResponseEntity<?> buildResponse(ApiResponse<?> response){
        if(response.isSuccess()){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(response.getErrorCode()).body(response);
    }




}
