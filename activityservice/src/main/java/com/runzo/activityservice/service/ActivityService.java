package com.runzo.activityservice.service;

import com.runzo.activityservice.dto.ActivityRequest;
import com.runzo.activityservice.dto.ActivityResponse;

interface ActivityService {
    ActivityResponse trackActivity(ActivityRequest request);

    ActivityResponse getActivityById(String activityId);
}
