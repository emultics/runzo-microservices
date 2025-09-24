package com.runzo.activityservice.service;

import com.runzo.common.logger.AppLogger;
import com.runzo.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class UserValidationService {

    @Autowired
    private WebClient userServiceWebClient;
    private final AppLogger appLogger = AppLogger.getInstance(UserValidationService.class);

    public boolean validateUser(String userId){
        if(userId==null|| userId.isBlank()){
                throw new RuntimeException("userId should not be null or empty");
        }
        try{
                ApiResponse<?> response = userServiceWebClient.get()
                        .uri("api/user/{userId}/validate", userId)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<ApiResponse<Boolean>>() {})
                        .block();
                if(response != null && Boolean.TRUE.equals(response.getData()) && response.isSuccess()){
                    return true;
                }
                appLogger.info("User validation failed for userId={}", userId);
                return false;
            }catch (WebClientResponseException ex){
                appLogger.error(ex.getMessage());
                return false;
            }catch (Exception ex){
            appLogger.error("Unexpected error while validating user "+userId,ex);
            return false;
        }
    }

}
