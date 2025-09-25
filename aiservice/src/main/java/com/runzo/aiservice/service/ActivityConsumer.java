package com.runzo.aiservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runzo.aiservice.dto.ActivityEvent;
import com.runzo.aiservice.model.Recommendation;
import com.runzo.aiservice.repository.RecommendationRepository;
import com.runzo.common.logger.AppLogger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ActivityConsumer {

    private final AppLogger logger = AppLogger.getInstance(ActivityConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    ActivityAIService activityAIService;

    @Autowired
    RecommendationRepository repository;


    @KafkaListener(
            topics = "${kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeActivity(String event) {
        logger.info("Consumed Activity: "+ event);
        ActivityEvent readPayload = null;
        try {
            readPayload = objectMapper.readValue(event, ActivityEvent.class);

            if (readPayload == null || readPayload.getActivity() == null) {
                logger.error("ActivityEvent or activity payload is null");
                return;
            }

            logger.info("Activity Id: {}", readPayload.getActivity().getId());
            Recommendation recommendation = activityAIService.generateRecommendation(readPayload.getActivity());
            logger.info("Saving recommendation to db");
            Recommendation savedEntity = repository.save(recommendation);

            if (savedEntity.getId() != null) {
                logger.info("Generated new RecommendationId: {} and saved to DB", savedEntity.getId().toHexString());
            } else {
                logger.error("Failed to save recommendation");
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }catch (Exception ex){
            throw new RuntimeException("Something Went Wrong: "+ex.getMessage());
        }


    }


}
