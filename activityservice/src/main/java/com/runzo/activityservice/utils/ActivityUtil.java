package com.runzo.activityservice.utils;

import com.runzo.activityservice.dto.ActivityRequest;
import com.runzo.activityservice.dto.ActivityResponse;
import com.runzo.activityservice.entity.Activity;

import java.time.Duration;
import java.time.LocalDateTime;

public class ActivityUtil {
    public static Activity mapToActivity(ActivityRequest request){
        return Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .durationMinutes(durationMinutes(request.getStartTime(), request.getEndTime()))
                .caloriesBurned(request.getCalories())
                .startTime(request.getStartTime())
                .distanceKm(request.getDistanceKm())
                .endTime(request.getEndTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();
    }

    public static ActivityResponse mapToActivityResponse(Activity activity){
        return ActivityResponse.builder()
                .Id(activity.getId().toHexString())
                .userId(activity.getUserId())
                .duration(activity.getDurationMinutes())
                .calories(activity.getCaloriesBurned())
                .startTime(activity.getStartTime())
                .endTime(activity.getEndTime())
                .type(activity.getType())
                .distanceKm(activity.getDistanceKm())
                .additionalMetrics(activity.getAdditionalMetrics())
                .createdAt(activity.getCreatedAt())
                .updatedAt(activity.getUpdatedAt()).build();
    }

    public static Integer durationMinutes(LocalDateTime startTime, LocalDateTime endTime){
        if(startTime!=null && endTime!=null){
            return  (int) Duration.between(startTime, endTime).toMinutes();
        }

        return -1;
    }
}
