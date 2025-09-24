package com.runzo.activityservice.service;

import com.runzo.activityservice.utils.ActivityUtil;
import com.runzo.activityservice.dto.ActivityRequest;
import com.runzo.activityservice.dto.ActivityResponse;
import com.runzo.activityservice.entity.Activity;
import com.runzo.activityservice.repository.ActivityRepository;
import com.runzo.common.exceptions.ex.ActivityNotFoundException;
import com.runzo.common.exceptions.ex.UserNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class ActivityServiceImpl implements ActivityService{


    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserValidationService userValidationService;

    @Override
    public ActivityResponse trackActivity(ActivityRequest request) {
        boolean isExistingUser = userValidationService.validateUser(request.getUserId());
        if(!isExistingUser){
            throw new UserNotFoundException("User not found! with userId: "+request.getUserId());
        }

        try {
            Activity activity = ActivityUtil.mapToActivity(request);
            Activity savedActivity = activityRepository.save(activity);
            return ActivityUtil.mapToActivityResponse(savedActivity);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public ActivityResponse getActivityById(String activityId){
        Activity activity = activityRepository.findById(new ObjectId(activityId)).orElseThrow(()->new ActivityNotFoundException("Activity not found!"));
        return ActivityUtil.mapToActivityResponse(activity);
    }
}
