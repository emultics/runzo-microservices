package com.runzo.aiservice.service;

import com.runzo.aiservice.dto.ActivityResponse;
import com.runzo.common.logger.AppLogger;
import com.runzo.common.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;
import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private WebClient webClient;

    @Value("${external.activity-service.baseurl}")
    private String activityServiceBaseUrl;

    private final static AppLogger logger = AppLogger.getInstance(ActivityAIService.class);


    public List<ActivityResponse> getActivitiesByUser(String userId) {
        if (userId == null || userId.isBlank()) {
            throw new RuntimeException("userId should not be null or empty");
        }

        String path = activityServiceBaseUrl + "/api/activities/search/user/{userId}/history";

        try {
            ApiResponse<List<ActivityResponse>> apiResponse = webClient
                    .get()
                    .uri(path, userId)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<ApiResponse<List<ActivityResponse>>>() {})
                    .block();

            if (apiResponse != null && apiResponse.isSuccess() && apiResponse.getData() != null) {
                logger.info(apiResponse.toString());
                return apiResponse.getData();
            } else {
                logger.error("Error during API fetch: {}"+ (apiResponse != null ? apiResponse.getMessage() : "null response"));
                return Collections.emptyList();
            }
        } catch (WebClientResponseException ex) {
            logger.error("WebClient error: "+ex.getMessage());
            return Collections.emptyList();
        } catch (Exception ex) {
            logger.error("Unexpected error while activity API call", ex);
            return Collections.emptyList();
        }
    }

}
