package com.fyp.moonlight.controller.users;

import com.fyp.moonlight.exceptions.EntityNotFoundException;
import com.fyp.moonlight.model.UserProfile;
import com.fyp.moonlight.service.UserProfileService;
import com.fyp.moonlight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @GetMapping("/all-users-profiles")
    public List<UserProfile> getAllUserProfiles() {
        return userProfileService.getAllUserProfiles();
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getUserProfile(Authentication authentication) throws EntityNotFoundException {
        var userId = userService.findUserByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Could not find authenticated user for email: " + authentication.getName()))
                .getId();

        var userProfileOptional = userProfileService.findLatestUserProfile(userId);
        return userProfileOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/profile")
    public ResponseEntity<UserProfile> createOrUpdateUserProfile(@RequestBody UserProfile userProfile,
                                                                 Authentication authentication) throws EntityNotFoundException {
        var userId = userService.findUserByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Could not find authenticated user for email: " + authentication.getName()))
                .getId();

//        checkUserProfileIsValid(userId, userProfile);

        var errorMessages = validator.validate(userProfile).stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());

        if (errorMessages.size() > 0) {
            return ResponseEntity.badRequest().body(userProfile);
        } else {
            var latestVersionNo = userProfileService.findLatestUserProfile(userId).map(UserProfile::getVersionNo).orElse(0);
            userProfile.setUserId(userId);
            userProfile.setVersionNo(latestVersionNo + 1);
            userProfile.setLastUpdatedDatetime(Instant.now());
            userProfileService.saveUserProfile(userProfile);

            return ResponseEntity.ok(userProfile);
        }
    }

    private void checkUserProfileIsValid(String userId, UserProfile userProfile) {
        if (!userService.doesUserExist(userId)) throw new IllegalStateException("Could not find user with id " + userId);

        if (!Objects.equals(userId, userProfile.getUserId())) {
            throw new IllegalStateException("User id in request and in user profile do not match.");
        }
    }

}
