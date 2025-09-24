package com.runzo.activityservice.repository;

import com.runzo.activityservice.entity.Activity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, ObjectId> {
    Optional<Activity> findByUserId(String userId);
    boolean existsByUserId(String userId);
}
