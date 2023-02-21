package com.fyp.moonlight.controller.users;

import com.fyp.moonlight.exceptions.EntityNotFoundException;
import com.fyp.moonlight.model.wellbeing.UserWellbeingStats;
import com.fyp.moonlight.model.wellbeing.WellbeingSurveySubmission;
import com.fyp.moonlight.repository.UserWellbeingStatsRepository;
import com.fyp.moonlight.service.UserService;
import com.fyp.moonlight.service.WellbeingService;
import com.google.common.collect.ArrayListMultimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Validator;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/api/users/profile/stats")
public class UserWellbeingStatsController {

    @Autowired
    private WellbeingService wellbeingService;

    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @Autowired
    private UserWellbeingStatsRepository userWellbeingStatsRepository;

    @GetMapping
    public ResponseEntity<UserWellbeingStats> getCurrentWellbeingStats(Authentication authentication) throws EntityNotFoundException {
        var userId = getAuthenticatedUserIdOrThrow(authentication);
        var latestScore = wellbeingService.findLatestUserWellbeingStats(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " does not have any previous wellbeing scores."));
        return ResponseEntity.ok(latestScore);
    }

    @GetMapping("/wb-type-to-submissions")
    public ResponseEntity<Map<Object, Collection<Object>>> getWellbeingTypeSurveySubmissions(Authentication authentication) throws EntityNotFoundException {
        return ResponseEntity.ok().body(wellbeingService.getWellbeingTypeToSubmissionsMapForUser(getAuthenticatedUserIdOrThrow(authentication)));
    }

    @GetMapping("/enrolment-to-submissions")
    public ResponseEntity<Map<Object, Collection<Object>>> getEnrolmentSurveySubmissions(Authentication authentication) throws EntityNotFoundException {
        return ResponseEntity.ok().body(wellbeingService.getProgrammeEnrolmentToSubmissionsMapForUser(getAuthenticatedUserIdOrThrow(authentication)));
    }

//    @GetMapping("/{userId}/profile")
//    public ResponseEntity<UserProfile> getUserProfile(@PathVariable String userId) {
//        var userProfileOptional = userProfileService.findLatestUserProfile(userId);
//        return userProfileOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @PostMapping("/{userId}/profile")
//    public ResponseEntity<UserProfile> createOrUpdateUserProfile(@PathVariable String userId,
//                                                                 @RequestBody UserProfile userProfile) {
//        checkUserProfileIsValid(userId, userProfile);
//
//        var errorMessages = validator.validate(userProfile).stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
//
//        if (errorMessages.size() > 0) {
//            return ResponseEntity.badRequest().body(userProfile);
//        } else {
//            var latestVersionNo = userProfileService.findLatestUserProfile(userId).map(UserProfile::getVersionNo).orElse(0);
//            userProfile.setVersionNo(latestVersionNo + 1);
//            userProfile.setLastUpdatedDatetime(Instant.now());
//            userProfileService.saveUserProfile(userProfile);
//
//            return ResponseEntity.ok(userProfile);
//        }
//    }

    private void checkSurveySubmissionIsValidForUser(String userId, WellbeingSurveySubmission submission) {
        if (!userService.doesUserExist(userId)) throw new IllegalStateException("Could not find user with id " + userId);

        if (!Objects.equals(userId, submission.getSubmittedByUserId())) {
            throw new IllegalStateException("User id in request and in survey submission do not match.");
        }
    }

    private String getAuthenticatedUserIdOrThrow(Authentication authentication) throws EntityNotFoundException {
        return userService.findUserByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Could not find authenticated user for email: " + authentication.getName()))
                .getId();
    }

}
