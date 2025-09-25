package com.runzo.activityservice.service;

import com.runzo.activityservice.dto.ActivityRequest;
import com.runzo.activityservice.dto.ActivityResponse;

import java.util.List;

interface ActivityService {
    ActivityResponse trackActivity(ActivityRequest request);

    ActivityResponse getActivityById(String activityId);

    List<ActivityResponse> getHistoryActivitiesByUserId(String userId);
}
