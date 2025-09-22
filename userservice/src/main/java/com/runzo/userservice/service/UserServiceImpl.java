package com.runzo.userservice.service;

import com.runzo.common.exceptions.ex.UserAlreadyExistException;
import com.runzo.userservice.dto.RegisterRequest;
import com.runzo.userservice.entity.User;
import com.runzo.userservice.repository.UserRepository;
import com.runzo.userservice.utils.CryptoManager;
import com.runzo.userservice.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    private final CryptoManager cryptoManager;

    @Autowired
    public UserServiceImpl(CryptoManager cryptoManager) {
        this.cryptoManager = cryptoManager;
    }

    @Override
    public User doRegister(RegisterRequest request){
        boolean isExists=userRepository.existsByEmailOrPhone(request.getEmail(), request.getPhone());
        if(isExists){
            throw new UserAlreadyExistException("User already Registered");
        }
        try{
            String encryptedPassword = cryptoManager.encrypt(request.getPassword());
            request.setPassword(encryptedPassword);
            User mappedUser = Utils.mapToUser(request);
            System.out.println(mappedUser.toString());
            return userRepository.save(mappedUser);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
