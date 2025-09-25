package com.runzo.aiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationResponse {

    private Analysis analysis;
    private List<Improvement> improvements;
    private List<Suggestion> suggestions;
    private List<String> safety;
    private Diet diet;
    private String contextualHints;
    private Object comparison; // dynamic field

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
