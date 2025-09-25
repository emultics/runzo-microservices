package com.runzo.activityservice.service;

import com.runzo.activityservice.dto.ActivityRequest;
import com.runzo.activityservice.dto.ActivityResponse;
import com.runzo.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityHandlerService {
    @Autowired
    ActivityService activityService;

    public ApiResponse<?> trackActivity(ActivityRequest request){
        ActivityResponse activity = activityService.trackActivity(request);
        if(activity==null){
            throw new RuntimeException("Activity Logging failed!");
        }

        return ApiResponse.success(activity, "new Activity created!");
    }

    public ApiResponse<?> getActivityById(String activityId){
        if(activityId == null){
            throw new RuntimeException("ActivityId is required!");
        }
        ActivityResponse response = activityService.getActivityById(activityId);
        return ApiResponse.success(response, "fetched activity successfully");
    }

    public ApiResponse<List<?>> getHistoryActivitiesByUserId(String userId){
        if(userId==null) {
            throw new RuntimeException("userId is required!");
        }

        List<ActivityResponse> activityResponses = activityService.getHistoryActivitiesByUserId(userId);
        return ApiResponse.success(activityResponses, "Fetched activities history!");
    }
}
