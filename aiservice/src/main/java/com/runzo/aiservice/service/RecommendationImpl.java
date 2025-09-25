package com.runzo.aiservice.service;

import com.runzo.aiservice.dto.RecommendationEndResponse;
import com.runzo.aiservice.dto.RecommendationResponse;
import com.runzo.aiservice.model.Recommendation;
import com.runzo.aiservice.repository.RecommendationRepository;
import com.runzo.aiservice.utils.CommonUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class RecommendationImpl implements RecommendationService{

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Override
    public List<RecommendationEndResponse> getUserRecommendation(String userId) {
        List<Recommendation> recommendations = recommendationRepository.findByUserId(userId);
        if(recommendations==null || recommendations.isEmpty()) throw new RuntimeException("No recommendations found!");
        return recommendations.stream()
                .map(CommonUtils::mapToResponse)
                .toList();
    }

    @Override
    public RecommendationEndResponse getActivityRecommendation(String activityId) {
        Recommendation recommendationForActivity = recommendationRepository.findByActivityId(new ObjectId(activityId)).orElseThrow(()-> new RuntimeException("No Recommendation found! for this activity! "));
        return CommonUtils.mapToResponse(recommendationForActivity);
    }
}
