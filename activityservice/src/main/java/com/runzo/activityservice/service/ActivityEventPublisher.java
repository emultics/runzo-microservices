package com.runzo.activityservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runzo.activityservice.dto.ActivityResponse;
import com.runzo.activityservice.utils.Action;
import com.runzo.activityservice.utils.ActivityEvent;
import com.runzo.common.logger.AppLogger;
import jakarta.ws.rs.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ActivityEventPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topicName;

    private static AppLogger appLogger = AppLogger.getInstance(ActivityEventPublisher.class);

    private final ObjectMapper objectMapper;

    public ActivityEventPublisher(KafkaTemplate<String, String> kafkaTemplate, @Value("${kafka.topic.name}") String topicName, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
        this.objectMapper = objectMapper;
    }

    public void publishActivity(ActivityResponse activity, Action action){
        try {
            ActivityEvent activityEvent = ActivityEvent.builder()
                    .action(action)
                    .activity(activity)
                    .timeStamp(LocalDateTime.now())
                    .build();

            String payload = objectMapper.writeValueAsString(activityEvent);
            appLogger.info("Publishing event: " + payload);

            kafkaTemplate.send(topicName, payload);

        } catch (JsonProcessingException e) {
            throw new InternalServerErrorException("Failed to serialize ActivityEvent", e);
        }

    }
}
