package com.runzo.aiservice.utils;

import com.runzo.aiservice.dto.ActivityResponse;
import com.runzo.common.logger.AppLogger;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SmartPromptBuilder {
    private static final AppLogger logger = AppLogger.getInstance(SmartPromptBuilder.class);
    private static final String PROMPT_TEMPLATE = """
        Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:
        {
          "analysis": {
            "overall": "Overall analysis here",
            "pace": "Pace analysis here",
            "heartRate": "Heart rate analysis here",
            "caloriesBurned": "Calories analysis here"
          },
          "improvements": [
            {
              "area": "Area name",
              "recommendation": "Detailed recommendation"
            }
          ],
          "suggestions": [
            {
              "workout": "Workout name",
              "description": "Detailed workout description"
            }
          ],
          "safety": [
            "Safety point 1",
            "Safety point 2"
          ],
          "diet": [
            "morning": "Dynamic based on user context",
            "evening": "Dynamic based on user context",
            "night": "Dynamic based on user context"
          ],
          "contextualHints": "Dynamic hints based on activity and user context",
          "comparison": %s
        }

        Activity Summary:
        Type: %s
        Duration: %d minutes
        Calories Burned: %.2f kcal
        Distance: %.2f km
        Additional Metrics: %s
        Start Time: %s
        End Time: %s
        Pace: %.2f min/km
        Calories Efficiency: %.2f kcal/km

        Provide detailed analysis focusing on performance, improvements, next workout suggestions, diet, safety, and contextual hints.
        Compare this activity with previous ones and highlight trends.
        Ensure the response strictly follows the EXACT JSON format above.
        """;

    private static final String HISTORY_METRICS = """
                        {
                          "type": "%s",
                          "duration": %d,
                          "calories": %.2f,
                          "distanceKm": %.2f,
                          "pace": %.2f,
                          "caloriesEfficiency": %.2f,
                          "startTime": "%s"
                        }
                        """;

    public static String createPrompt(ActivityResponse activity, List<ActivityResponse> history){
        return String.format(PROMPT_TEMPLATE,
                buildComparisonJson(history),
                safeString(activity.getType()),
                safeInt(activity.getDuration()),
                safeDouble(activity.getCalories()),
                safeDouble(activity.getDistanceKm()),
                safeString(activity.getAdditionalMetrics()),
                safeString(activity.getStartTime()),
                safeString(activity.getEndTime()),
                calculatePace(activity),
                calculateCaloriesEfficiency(activity)
        );
    }

    private static String buildComparisonJson(List<ActivityResponse> history){
        if(history==null || history.isEmpty()) return "[]";

        return history.stream()
                .map(activity-> String.format(HISTORY_METRICS,safeString(activity.getType()),
                        safeInt(activity.getDuration()),
                        safeDouble(activity.getCalories()),
                        safeDouble(activity.getDistanceKm()),
                        calculatePace(activity),
                        calculateCaloriesEfficiency(activity),
                        safeString(activity.getStartTime()))).collect(Collectors.joining(", ", "[","]"));
    }

    private static String safeString(Object obj){
        return obj==null?"N/A":obj.toString();
    }

    private static int safeInt(Integer value){
        return value!=null?value:0;
    }

    private static double safeDouble(Double value){
        return value!=null?value:0.0;
    }

    private static String safeString(LocalDateTime dateTime) {
        if (dateTime == null) return "N/A";
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    private static double calculatePace(ActivityResponse activity){
        if(activity.getDistanceKm()==null || activity.getDistanceKm()==0) return 0.0;
        logger.info("Getting duration: {}", activity.getDuration());
        return activity.getDuration()/activity.getDistanceKm();
    }

    private static double calculateCaloriesEfficiency(ActivityResponse activity){
        if(activity.getDistanceKm()==null || activity.getDistanceKm()==0) return 0.0;
        logger.info("Getting Calories: {}", activity.getCalories());
        logger.info("Getting Distance in Km: {}", activity.getDistanceKm());

        return activity.getCalories()/activity.getDistanceKm();
    }
}
