package com.runzo.aiservice.repository;

import com.runzo.aiservice.model.Recommendation;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationRepository extends MongoRepository<Recommendation, ObjectId> {
    List<Recommendation> findByUserId(String userId);
    Optional<Recommendation> findByActivityId(ObjectId activityId);
}
