package com.fyp.moonlight.service;

import com.fyp.moonlight.model.UserProfile;
import com.fyp.moonlight.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public List<UserProfile> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }

    public Optional<UserProfile> findLatestUserProfile(String userId) {
        return userProfileRepository.findTopByUserIdOrderByVersionNoDesc(userId);
    }

    public UserProfile saveUserProfile(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }


}
