package com.fyp.moonlight.controller;

import com.fyp.moonlight.exceptions.EntityNotFoundException;
import com.fyp.moonlight.exceptions.ProgrammeEnrolmentException;
import com.fyp.moonlight.model.ProgrammeEnrolment;
import com.fyp.moonlight.model.wellbeing.WellbeingTypeWrapper;
import com.fyp.moonlight.model.wellbeingProgrammes.EnrolmentStatus;
import com.fyp.moonlight.model.wellbeingProgrammes.WellbeingProgramme;
import com.fyp.moonlight.repository.WellbeingProgrammeRepository;
import com.fyp.moonlight.repository.ProgrammeEnrolmentRepository;
import com.fyp.moonlight.service.ProgrammeEnrolmentService;
import com.fyp.moonlight.service.UserService;
import com.fyp.moonlight.service.WellbeingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Comparator;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enrolments")
public class ProgrammeEnrolmentController {

    @Autowired
    private ProgrammeEnrolmentRepository programmeEnrolmentRepository;

    @Autowired
    private WellbeingProgrammeRepository wellbeingProgrammeRepository;

    @Autowired
    private WellbeingService wellbeingService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProgrammeEnrolmentService programmeEnrolmentService;

    @GetMapping("/current")
    public ResponseEntity<Object> getCurrentEnrolment(Authentication authentication) throws EntityNotFoundException, ProgrammeEnrolmentException {
        var userId = getAuthenticatedUserIdOrThrow(authentication);
        var activeEnrolment = programmeEnrolmentRepository.findByUserIdAndEnrolmentStatus(userId, EnrolmentStatus.ACTIVE)
                .orElseThrow(() -> new ProgrammeEnrolmentException("User is not currently enrolled in any programme."));
        var activeEnrolmentView = programmeEnrolmentService.getProgrammeEnrolmentSnapshot(activeEnrolment);
        return ResponseEntity.ok().body(activeEnrolmentView);
    }

    @GetMapping("/{enrolmentId}")
    public ResponseEntity<Object> getEnrolmentDetails(@PathVariable String enrolmentId) throws EntityNotFoundException {
        var enrolment = programmeEnrolmentRepository.findById(enrolmentId)
                .orElseThrow(() -> new EntityNotFoundException("Could not find enrolment with id " + enrolmentId));
        var enrolmentView = programmeEnrolmentService.getProgrammeEnrolmentView(enrolment);
        return ResponseEntity.ok().body(enrolmentView);
    }

    @PostMapping("/{programmeId}/enrol")
    public ResponseEntity<Object> enrolUserToProgramme(@PathVariable String programmeId,
                                                       @RequestBody WellbeingTypeWrapper selectedWellbeingType,
                                                       Authentication authentication) throws EntityNotFoundException, ProgrammeEnrolmentException {
        var userId = getAuthenticatedUserIdOrThrow(authentication);

        //check current enrolments
        if (programmeEnrolmentRepository.findByUserIdAndEnrolmentStatus(userId, EnrolmentStatus.ACTIVE).isPresent()) {
            throw new ProgrammeEnrolmentException("You are already enrolled in a programme");
        }

        var enrolment = new ProgrammeEnrolment(userId, programmeId, selectedWellbeingType.getSelectedWellbeingType(), Instant.now(), 1, EnrolmentStatus.ACTIVE);
        programmeEnrolmentRepository.save(enrolment);

        return ResponseEntity.ok().body("Successfully enrolled in programme");
    }

    @PostMapping("/current/leave")
    public ResponseEntity<String> removeUserFromProgramme(Authentication authentication) throws EntityNotFoundException {
        var userId = getAuthenticatedUserIdOrThrow(authentication);
        var currentEnrolment = getCurrentEnrolmentOrThrow(userId);
        currentEnrolment.setEnrolmentStatus(EnrolmentStatus.ABANDONED);
        currentEnrolment.setTerminationTimestamp(Instant.now());
        programmeEnrolmentRepository.save(currentEnrolment);
        return ResponseEntity.ok().body("Successfully left programme.");
    }

    @PostMapping("/current/progress")
    public ResponseEntity<String> progressUserToNextStage(Authentication authentication) throws EntityNotFoundException, ProgrammeEnrolmentException {
        var userId = getAuthenticatedUserIdOrThrow(authentication);
        var currentEnrolment = getCurrentEnrolmentOrThrow(userId);
        var programme = getProgrammeOrThrow(currentEnrolment.getProgrammeId());
        var programmeStagesKeys = programme.getProgrammeStageMap().keySet().stream()
                .sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        var successMessage = "";

        //check user has submitted a survey for the current stage before progressing further
        checkUserHasSubmittedSurveyOrThrow(currentEnrolment);

        //check if programme has following stage and progress, otherwise complete
        if (programmeStagesKeys.stream()
                .anyMatch(programmeStageKey -> programmeStageKey > currentEnrolment.getCurrentStage())) {
            currentEnrolment.setCurrentStage(
                    programmeStagesKeys.stream()
                            .sorted()
                            .filter(stageKey -> stageKey > currentEnrolment.getCurrentStage()).findFirst()
                            .get()
            );
            successMessage = "Successfully progressed to next stage";
        } else {
            currentEnrolment.setEnrolmentStatus(EnrolmentStatus.COMPLETED);
            currentEnrolment.setTerminationTimestamp(Instant.now());
            successMessage = "Successfully completed programme";
        }

        programmeEnrolmentRepository.save(currentEnrolment);
        return ResponseEntity.ok(successMessage);
    }

    private ProgrammeEnrolment getCurrentEnrolmentOrThrow(String userId) {
        return programmeEnrolmentRepository.findByUserIdAndEnrolmentStatus(userId, EnrolmentStatus.ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException(
                                "Cannot find current enrolment for user " + userId + " in any programme."
                        )
                );
    }

    private WellbeingProgramme getProgrammeOrThrow(String programmeId) {
        return wellbeingProgrammeRepository.findById(programmeId)
                .orElseThrow(() -> new IllegalArgumentException("Could not find programme with id " + programmeId));
    }

    private String getAuthenticatedUserIdOrThrow(Authentication authentication) throws EntityNotFoundException {
        return userService.findUserByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Could not find authenticated user for email: " + authentication.getName()))
                .getId();
    }

    private void checkUserHasSubmittedSurveyOrThrow(ProgrammeEnrolment currentEnrolment) throws ProgrammeEnrolmentException {
        if (!wellbeingService.hasUserSubmittedSurveyForCurrentProgrammeStage(currentEnrolment)) {
            throw new ProgrammeEnrolmentException("Please submit a wellbeing survey for this programme stage before progressing.");
        }
    }

}
