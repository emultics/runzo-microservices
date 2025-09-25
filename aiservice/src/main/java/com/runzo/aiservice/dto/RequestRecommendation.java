package com.runzo.aiservice.dto;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

public class RequestRecommendation {
    private String activityId;
    private String userId;
    private String recommendation;
    private List<String> suggestions;

    private List<String> safety;
}
