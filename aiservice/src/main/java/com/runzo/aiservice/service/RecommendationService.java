package com.runzo.aiservice.service;

import com.runzo.aiservice.dto.RecommendationEndResponse;
import com.runzo.aiservice.dto.RecommendationResponse;
import com.runzo.aiservice.model.Recommendation;
import org.springframework.stereotype.Service;

import java.util.List;


interface RecommendationService {

    public List<RecommendationEndResponse> getUserRecommendation(String userId);
    public RecommendationEndResponse getActivityRecommendation(String activityId);

}
