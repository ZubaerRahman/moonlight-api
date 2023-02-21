package com.fyp.moonlight.repository;

import com.fyp.moonlight.model.wellbeing.UserWellbeingStats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserWellbeingStatsRepository extends MongoRepository<UserWellbeingStats, String> {
    Optional<UserWellbeingStats> findTopByUserIdOrderByVersionNoDesc(String userId);
}
