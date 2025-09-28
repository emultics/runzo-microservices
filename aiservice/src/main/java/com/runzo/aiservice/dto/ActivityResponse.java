package com.runzo.aiservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityResponse {

    @JsonProperty("id")
    private String Id;

    @JsonProperty("user_id")
    private String userId;

    private ActivityType type;

    private Integer duration;

    private double calories;

    @JsonProperty("distance_km")
    private Double distanceKm;

    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonProperty("end_time")
    private LocalDateTime endTime;

    @JsonProperty("additional_metrics")
    private Map<String, Object> additionalMetrics;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
