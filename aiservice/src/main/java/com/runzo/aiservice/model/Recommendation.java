package com.runzo.aiservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "runzo_recommendation")
@Data
@Builder
public class Recommendation {
    @Id
    private ObjectId id;

    @Field("activity_id")
    private ObjectId activityId;

    @Field("user_id")
    private String userId;

    private Analysis analysis;
    private List<Improvement> improvements;
    private List<Suggestion> suggestions;
    private List<String> safety;
    private Diet diet;
    private String contextualHints;
    private Object comparison;


    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Analysis {
        private String overall;
        private String pace;
        private String heartRate;
        private String caloriesBurned;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Improvement {
        private String area;
        private String recommendation;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Suggestion {
        private String workout;
        private String description;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Diet {
        private String morning;
        private String evening;
        private String night;
    }
}
