package com.runzo.aiservice.controller;

import com.runzo.aiservice.service.RecommendationHandler;
import com.runzo.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired
    RecommendationHandler recommendationHandler;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserRecommendation(@PathVariable("userId") String userId){
        ApiResponse<?> response = recommendationHandler.getUserRecommendation(userId);
        return ApiResponse.buildResponse(response);
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<ApiResponse<?>> getActivityRecommendation(@PathVariable("activityId") String activityId){
        ApiResponse<?> response = recommendationHandler.getActivityRecommendation(activityId);
        return ApiResponse.buildResponse(response);
    }


}
