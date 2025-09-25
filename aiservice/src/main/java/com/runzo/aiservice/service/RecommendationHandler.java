package com.runzo.aiservice.service;

import com.runzo.aiservice.dto.RecommendationEndResponse;
import com.runzo.aiservice.dto.RecommendationResponse;
import com.runzo.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationHandler {
    @Autowired
    RecommendationService recommendationService;

    public ApiResponse<?> getUserRecommendation(String userId){
        if(userId==null || userId.isBlank()) throw new RuntimeException("UserId is required!");
        List<RecommendationEndResponse> response = recommendationService.getUserRecommendation(userId);
        return ApiResponse.success(response, "recommendation found!");
    }
    public ApiResponse<?> getActivityRecommendation(String activityId){
        if(activityId==null || activityId.isBlank()) throw new RuntimeException("activityId is required!");
        RecommendationEndResponse response = recommendationService.getActivityRecommendation(activityId);
        return ApiResponse.success(response, "recommendation found!");
    }
}
