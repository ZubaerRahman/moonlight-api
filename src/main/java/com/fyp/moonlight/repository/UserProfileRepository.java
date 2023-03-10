package com.fyp.moonlight.repository;

import com.fyp.moonlight.model.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfile, String> {

    Optional<UserProfile> findTopByUserIdOrderByVersionNoDesc(String userId);

}
