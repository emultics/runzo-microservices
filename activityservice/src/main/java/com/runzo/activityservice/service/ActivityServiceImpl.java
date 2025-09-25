package com.runzo.activityservice.service;

import com.runzo.activityservice.utils.Action;
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

import java.util.List;
import java.util.UUID;

@Service
class ActivityServiceImpl implements ActivityService{


    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserValidationService userValidationService;

    @Autowired
    private ActivityEventPublisher publisher;

    @Override
    public ActivityResponse trackActivity(ActivityRequest request) {
        checkUserIsExistOrNot(request.getUserId());

        try {
            Activity activity = ActivityUtil.mapToActivity(request);
            Activity savedActivity = activityRepository.save(activity);

            /**
             * future enhancement
             * if db fails or db down
             * handle via outbox processor
             * goes to outbox db
             * then each 5ms, kafka process record to publish
             * and reupdate or retry to save to activityDB
             * ensure Strong consistency between Mongo and Kafka.
             * Use either a Spring scheduled processor or Debezium CDC to push to Kafka.
             */

            ActivityResponse response = ActivityUtil.mapToActivityResponse(savedActivity);
            publisher.publishActivity(response, Action.CREATE); // produce message to kafka
            return response;
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    private void checkUserIsExistOrNot(String userId) {
        boolean isExistingUser = userValidationService.validateUser(userId);
        if(!isExistingUser){
            throw new UserNotFoundException("User not found! with userId: "+ userId);
        }
    }

    @Override
    public ActivityResponse getActivityById(String activityId) {
        System.out.println("activityId: " + activityId);

        ObjectId objectId;
        try {
            objectId = new ObjectId(activityId);
        } catch (IllegalArgumentException e) {
            throw new ActivityNotFoundException("Invalid activityId format: " + activityId);
        }

        Activity activity = activityRepository.findById(objectId)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found!"));

        return ActivityUtil.mapToActivityResponse(activity);
    }

    @Override
    public List<ActivityResponse> getHistoryActivitiesByUserId(String userId) {
        checkUserIsExistOrNot(userId);
        return activityRepository.findByUserId(userId).stream()
                .map(ActivityUtil::mapToActivityResponse)
                .toList();
    }
}
