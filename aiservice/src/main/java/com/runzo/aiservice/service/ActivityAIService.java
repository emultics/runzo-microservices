package com.runzo.aiservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runzo.aiservice.dto.ActivityResponse;
import com.runzo.aiservice.dto.RecommendationResponse;
import com.runzo.aiservice.model.Recommendation;
import com.runzo.aiservice.repository.RecommendationRepository;
import com.runzo.aiservice.utils.SmartPromptBuilder;
import com.runzo.common.logger.AppLogger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityAIService {
    private final static  AppLogger logger = AppLogger.getInstance(ActivityAIService.class);

    @Autowired
    private GeminiService geminiService;
    @Autowired
    ActivityService activityService;

    @Autowired
    RecommendationRepository recommendationRepository;



    public Recommendation generateRecommendation(ActivityResponse activity){
        List<ActivityResponse> activityHistory = activityService.getActivitiesByUser(activity.getUserId());
        logger.info("Collected Past activities for User {} : count {}", activity.getUserId(), activityHistory.size());
        logger.info("Generating Response .. ");

        String prompt = SmartPromptBuilder.createPrompt(activity, activityHistory);
        String response = geminiService.getRecommendations(prompt);
        logger.info("RESPONSE FROM MODEL: {}", response);

        return processResponseFromModel(activity, response);
    }

    private Recommendation processResponseFromModel(ActivityResponse activity, String modelResponse){
        try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(modelResponse);
            JsonNode textNode = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .get("parts")
                    .get(0)
                    .path("text");
            String jsonContent = textNode.asText()
                    .replaceAll("```json\\n", "")
                    .replaceAll("\\n```","")
                    .trim();

            RecommendationResponse recommendationResponse = mapper.readValue(
                    jsonContent,
                    RecommendationResponse.class
            );
            logger.info("RESPONSE FROM MODEL CLEANED: {}", jsonContent);

            return Recommendation.builder()
                    .activityId(new ObjectId(activity.getId()))
                    .userId(activity.getUserId())
                    .analysis(mapAnalysis(recommendationResponse.getAnalysis()))
                    .improvements(mapImprovement(recommendationResponse.getImprovements()))
                    .suggestions(mapSuggestions(recommendationResponse.getSuggestions()))
                    .diet(mapDiet(recommendationResponse.getDiet()))
                    .comparison(recommendationResponse.getComparison())
                    .safety(recommendationResponse.getSafety())
                    .contextualHints(recommendationResponse.getContextualHints())
                    .build();


        } catch (JsonProcessingException e) {
            logger.error("Something Went Wrong while parsing : "+e.getMessage());
            logger.info("Default Recommendation has been processed");
            return createDefaultRecommendation(activity);

        }
    }

    private Recommendation.Analysis mapAnalysis(RecommendationResponse.Analysis analysis){
        if(analysis==null) return  null;
        return Recommendation.Analysis.builder()
                .overall(analysis.getOverall())
                .pace(analysis.getPace())
                .heartRate(analysis.getHeartRate())
                .caloriesBurned(analysis.getCaloriesBurned())
                .build();
    }

    private List<Recommendation.Improvement> mapImprovement(List<RecommendationResponse.Improvement> improvement){
        if(improvement==null) return null;
        return improvement.stream()
                .map(imp -> Recommendation.Improvement.builder()
                        .area(imp.getArea())
                        .recommendation(imp.getRecommendation())
                        .build()
                ).toList();
    }

    private List<Recommendation.Suggestion> mapSuggestions(List<RecommendationResponse.Suggestion> suggestions) {
        if (suggestions == null) return null;
        return suggestions.stream()
                .map(suggestion -> Recommendation.Suggestion.builder()
                        .workout(suggestion.getWorkout())
                        .description(suggestion.getDescription())
                        .build())
                .toList();
    }

    private Recommendation.Diet mapDiet(RecommendationResponse.Diet diet) {
        if (diet == null) return null;
        return Recommendation.Diet.builder()
                .morning(diet.getMorning())
                .evening(diet.getEvening())
                .night(diet.getNight())
                .build();
    }

    private Recommendation createDefaultRecommendation(ActivityResponse activity) {
        return Recommendation.builder()
                .activityId(new org.bson.types.ObjectId(activity.getId()))
                .userId(activity.getUserId())
                .analysis(Recommendation.Analysis.builder()
                        .overall("No analysis available")
                        .pace("N/A")
                        .heartRate("N/A")
                        .caloriesBurned("N/A")
                        .build())
                .improvements(List.of(
                        Recommendation.Improvement.builder()
                                .area("General")
                                .recommendation("No improvement data available")
                                .build()))
                .suggestions(List.of(
                        Recommendation.Suggestion.builder()
                                .workout("General Activity")
                                .description("No suggestions available")
                                .build()))
                .safety(List.of(
                        "No specific safety instructions"))
                .diet(Recommendation.Diet.builder()
                        .morning("Balanced breakfast")
                        .evening("Light dinner")
                        .night("Hydrate well")
                        .build())
                .contextualHints("Default recommendation due to processing error")
                .comparison(null)
                .build();
    }





}
