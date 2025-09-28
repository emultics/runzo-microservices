package com.runzo.aiservice.utils;

import com.runzo.aiservice.dto.RecommendationEndResponse;
import com.runzo.aiservice.dto.RecommendationResponse;
import com.runzo.aiservice.model.Recommendation;
import org.bson.types.ObjectId;

import java.util.List;

public class CommonUtils {
    public static Recommendation mapToRecommendation(){
        return null;
    }

    public static RecommendationEndResponse mapToResponse(Recommendation recommendation){
       return RecommendationEndResponse.builder()
               .id(recommendation.getId().toHexString())
                .activityId(recommendation.getActivityId().toHexString())
                .userId(recommendation.getUserId())
                .analysis(mapAnalysis(recommendation.getAnalysis()))
                .improvements(mapImprovement(recommendation.getImprovements()))
                .suggestions(mapSuggestions(recommendation.getSuggestions()))
                .diet(mapDiet(recommendation.getDiet()))
                .comparison(recommendation.getComparison())
                .safety(recommendation.getSafety())
                .contextualHints(recommendation.getContextualHints())
               .createdAt(recommendation.getCreatedAt())
               .updatedAt(recommendation.getUpdatedAt())
                .build();

    }

    private static RecommendationEndResponse.Analysis mapAnalysis(Recommendation.Analysis analysis) {
        if (analysis == null) return null;
        return RecommendationEndResponse.Analysis.builder()
                .pace(analysis.getPace())
                .caloriesBurned(analysis.getCaloriesBurned())
                .overall(analysis.getOverall())
                .heartRate(analysis.getHeartRate())
                .build();
    }

    private static List<RecommendationEndResponse.Improvement> mapImprovement(List<Recommendation.Improvement> improvement){
        if(improvement==null) return null;
        return improvement.stream()
                .map(imp -> RecommendationEndResponse.Improvement.builder()
                        .area(imp.getArea())
                        .recommendation(imp.getRecommendation())
                        .build()
                ).toList();
    }

    private static List<RecommendationEndResponse.Suggestion> mapSuggestions(List<Recommendation.Suggestion> suggestions) {
        if (suggestions == null) return null;
        return suggestions.stream()
                .map(suggestion -> RecommendationEndResponse.Suggestion.builder()
                        .workout(suggestion.getWorkout())
                        .description(suggestion.getDescription())
                        .build())
                .toList();
    }

    private static RecommendationEndResponse.Diet mapDiet(Recommendation.Diet diet) {
        if (diet == null) return null;
        return RecommendationEndResponse.Diet.builder()
                .morning(diet.getMorning())
                .evening(diet.getEvening())
                .night(diet.getNight())
                .build();
    }
}

