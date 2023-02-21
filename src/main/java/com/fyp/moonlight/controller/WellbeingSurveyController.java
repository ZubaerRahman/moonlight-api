package com.fyp.moonlight.controller;

import com.fyp.moonlight.exceptions.EntityNotFoundException;
import com.fyp.moonlight.exceptions.FormValidationException;
import com.fyp.moonlight.exceptions.ProgrammeEnrolmentException;
import com.fyp.moonlight.model.wellbeing.WellbeingSurveySubmission;
import com.fyp.moonlight.model.wellbeing.WellbeingSurveyType;
import com.fyp.moonlight.model.wellbeing.WellbeingType;
import com.fyp.moonlight.model.wellbeingProgrammes.ProgrammeEnrolmentSnapshot;
import com.fyp.moonlight.service.ProgrammeEnrolmentService;
import com.fyp.moonlight.service.UserService;
import com.fyp.moonlight.service.WellbeingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wellbeing")
public class WellbeingSurveyController {

    @Autowired
    public WellbeingService wellbeingService;

    @Autowired
    public UserService userService;

    @Autowired
    public ProgrammeEnrolmentService programmeEnrolmentService;

    @GetMapping("wellbeing-types")
    public List<WellbeingType> getWellbeingTypes() {
        return Arrays.asList(WellbeingType.values());
    }

    @GetMapping("{wellbeingSurveyType}")
    public ResponseEntity<WellbeingSurveyType> getSurvey(@PathVariable WellbeingSurveyType wellbeingSurveyType) {
        return new ResponseEntity<WellbeingSurveyType>(wellbeingSurveyType, HttpStatus.ACCEPTED);
    }

    //user can optionally indicate whether the survey submission is related to a programme, and if it is, we need to make sure the user submits one survey per programme stage
    @PostMapping("{wellbeingSurveyType}")
    public WellbeingSurveySubmission submitSurvey(
            @PathVariable WellbeingSurveyType wellbeingSurveyType,
            @RequestBody Map<String, String> formAnswers,
            Authentication authentication) throws FormValidationException, EntityNotFoundException, ProgrammeEnrolmentException {

        var errors = new ArrayList<String>();
        var surveyAnswersMap = formAnswers.keySet().stream()
                .filter(key -> !key.equals("user-comment")
                        && !key.equals("related-to-enrolment"))
                .collect(Collectors.toMap(Integer::parseInt, key -> Integer.parseInt(formAnswers.get(key))));
        var userComment = formAnswers.get("user-comment");
        var isRelatedToCurrentEnrolment = Boolean.parseBoolean(formAnswers.get("related-to-enrolment"));

        //validate submission and throw error if invalid
        if (surveyAnswersMap.keySet().size() != wellbeingSurveyType.getQuestions().size()) {
            errors.add("Please answer all the required survey questions.");
        }

        var userId = getAuthenticatedUserIdOrThrow(authentication);
        var sureveySubmission = new WellbeingSurveySubmission(userId, Instant.now(), wellbeingSurveyType, surveyAnswersMap, userComment);

        if (isRelatedToCurrentEnrolment) {
            var currentEnrolment = programmeEnrolmentService.getCurrentProgrammeEnrolmentForUser(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User is not currently enrolled in a programme."));
            var enrolmentSnapshot = programmeEnrolmentService.getProgrammeEnrolmentSnapshot(currentEnrolment)
                    .orElseThrow(() -> new ProgrammeEnrolmentException("Could not link survey submission to current programme enrolment."));

            checkUserCanSubmitSurvey(userId, wellbeingSurveyType, enrolmentSnapshot, errors);

            sureveySubmission.setProgrammeEnrolmentSnapshot(enrolmentSnapshot);
        }

        if (errors.size() > 0) {
            throw new FormValidationException("Errors in form", errors);
        }

        wellbeingService.saveSurveyAndUpdateStats(sureveySubmission);
        return sureveySubmission;
    }

    @GetMapping("/get-survey-types")
    public Map<String, String> getAllSurveyTypeNames() {
        return WellbeingSurveyType.getEnumNameMap();
    }

    private String getAuthenticatedUserIdOrThrow(Authentication authentication) throws EntityNotFoundException {
        return userService.findUserByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Could not find authenticated user for email: " + authentication.getName()))
                .getId();
    }

    // users cannot submit multiple surveys for the same programme stage
    // users cannot submit a survey related to an enrolment if the survey type doesn't cover the same type of wellbeing that their enrolment addresses
    public void checkUserCanSubmitSurvey(String userId, WellbeingSurveyType wellbeingSurveyType, ProgrammeEnrolmentSnapshot currentEnrolmentSnapshot, List<String> errors) {
        if (wellbeingSurveyType.getWellbeingType() != currentEnrolmentSnapshot.getFocusedWellbeingType()) {
            errors.add("You cannot submit a " + wellbeingSurveyType.getName()
                    + " survey for this programme as your current programme goal is to improve your "
                    + currentEnrolmentSnapshot.getFocusedWellbeingType().name() + " wellbeing.");
            return;
        }

        var enrolmentSnapshotsForSubmissionsWithProgramme = wellbeingService.getSurveySubmissionsRelatedToProgrammeEnrolmentByUser(userId).stream()
                .map(WellbeingSurveySubmission::getProgrammeEnrolmentSnapshot)
                .collect(Collectors.toList());
        var areThereSubmissionsForSameStageAndEnrolment = enrolmentSnapshotsForSubmissionsWithProgramme.stream()
                .anyMatch(enrolmentSnapshot ->
                        enrolmentSnapshot.getEnrolmentId().equals(currentEnrolmentSnapshot.getEnrolmentId())
                        && enrolmentSnapshot.getCurrentStage() == currentEnrolmentSnapshot.getCurrentStage()
                );
        if (areThereSubmissionsForSameStageAndEnrolment) {
            errors.add("You have already submitted a survey for this programme stage. Please progress to the next stage.");
        }
    }

}
