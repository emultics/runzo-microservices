package com.runzo.userservice.controller;

import com.runzo.common.crypto.CryptoProcessor;
import com.runzo.common.crypto.CryptoService;
import com.runzo.common.crypto.strategy.CryptoType;
import com.runzo.common.response.ApiResponse;
import com.runzo.userservice.dto.RegisterRequest;
import com.runzo.userservice.service.UserService;
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

    @PostMapping
    public ResponseEntity<ApiResponse<?>> doRegister(@RequestBody RegisterRequest request){
        CryptoService service = new CryptoProcessor(CryptoType.BCRYPT, "12330130101");
        String encrypted = service.encrypt(request.getEmail());
        return ResponseEntity.ok(ApiResponse.success(encrypted, "e"));
    }



}
