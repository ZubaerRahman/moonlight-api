package com.fyp.moonlight.controller;

import com.fyp.moonlight.exceptions.EntityNotFoundException;
import com.fyp.moonlight.model.wellbeingProgrammes.*;
import com.fyp.moonlight.repository.WellbeingProgrammeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/programmes")
public class WellbeingProgrammeController {

    @Autowired
    private WellbeingProgrammeRepository wellbeingProgrammeRepository;

    @GetMapping
    public ResponseEntity<List<WellbeingProgramme>> getAllProgrammes() {
        return ResponseEntity.ok().body(wellbeingProgrammeRepository.findAll());
    }

    @GetMapping("/create-test-programme")
    private WellbeingProgramme createTestWellbeingProgramme() {
        wellbeingProgrammeRepository.deleteAll();

        var testProgramme = new WellbeingProgramme("Test programme name", "Test programme desc", 8);

        return wellbeingProgrammeRepository.save(testProgramme);
    }

    @PostMapping("/create-programme")
    private WellbeingProgramme createWellbeingProgramme(@RequestBody WellbeingProgramme wellbeingProgramme) {
        if (wellbeingProgramme.getName() != null
                && wellbeingProgramme.getDescription() != null
                && wellbeingProgramme.getDurationInWeeks() != null) {
            return wellbeingProgrammeRepository.save(wellbeingProgramme);
        } else throw new IllegalArgumentException("Invalid arguments to create a new wellbeing programme");
    }

    @GetMapping("/{programmeId}/add-test-programme-stage")
    private WellbeingProgramme addTestProgrammeStage(@PathVariable String programmeId) {
        var programme = wellbeingProgrammeRepository.findById(programmeId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find programme with ID"));

        var programmeStage = new ProgrammeStage(" test stage");
        programme.setProgrammeStageMap(Map.of(1, programmeStage));

        return wellbeingProgrammeRepository.save(programme);
    }

//    @GetMapping("/{programmeId}/add-second-test-programme-stage")
//    private WellbeingProgramme addSecondTestProgrammeStage(@PathVariable String programmeId) {
//        var programme = wellbeingProgrammeRepository.findById(programmeId)
//                .orElseThrow(() -> new IllegalArgumentException("Cannot find programme with ID"));
//        var programmeStage = new ProgrammeStage(" test stage");
//
//        programme.getProgrammeStageMap().put(3, programmeStage);
//
//        return wellbeingProgrammeRepository.save(programme);
//    }

//    @GetMapping("/{programmeId}/add-third-test-programme-stage")
//    private WellbeingProgramme addAThirdTestProgrammeStage(@PathVariable String programmeId) {
//        var programme = wellbeingProgrammeRepository.findById(programmeId)
//                .orElseThrow(() -> new IllegalArgumentException("Cannot find programme with ID"));
//        var programmeStage = new ProgrammeStage(" test stage");
//
//        programme.getProgrammeStageMap().put(5, programmeStage);
//
//        return wellbeingProgrammeRepository.save(programme);
//    }

    @GetMapping("/{programmeId}/add-test-activities-to-stage")
    private WellbeingProgramme addTestActivityToProgrammeStage(@PathVariable String programmeId) {
        var programme = wellbeingProgrammeRepository.findById(programmeId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find programme with ID"));

        var programmeStage = programme.getProgrammeStageMap().get(1);
//        programmeStage.setActivityMap(Map.of(
//                1, new ResistanceActivity("Bench press", "Fantastic excercise for chest, shoulders and triceps", 6, 8, 25),
//                2, new ResistanceActivity("Cable chest flys", "Build a bulletproof chest", 4, 12, 20),
//                3, new ResistanceActivity("Tricep pulldown", "Great excercise to isolate triceps", 4, 10, 18),
//                4, new ResistanceActivity("Freeweight dips", "Great compound push excercise", 4, 8, 20)));

        programmeStage.setActivityMap(Map.of(
                1, new MindfulnessActivity("Mindfulness meditation", "Learn to focus on the present with a 15 minute mindfulness meditation", 15),
                2, new MindfulnessActivity("More mindfulness meditation", "Practice focusing on your breathing with another 15 minute mindfulness meditation", 15),
                3, new MindfulnessActivity("Yoga", "Yoga improves strength, balance and flexibility, while helping you relax", 30),
                4, new MindfulnessActivity("Mindfulness meditation", "Learn to focus on the present with a 20 minute mindfulness meditation", 20)
                ));

//        programmeStage.setActivityMap(Map.of(
//                1, new CardioActivity("Jog in the park", "Get your heart rate up with an revitalizing morning jog", 45),
//                2, new CardioActivity("Star jumps", "Learn to focus on the present with a 15 minute mindfulness meditation", 15),
//                3, new ResistanceActivity("Bodyweight squats", "Learn to focus on the present with a 15 minute mindfulness meditation", 4, 15, 20),
//                4, new CardioActivity("Burpees", "Lets finish off with as many burpess as you can", 1)
//                )
//        );

//        programmeStage.setActivityMap(Map.of(
//                1, new ResistanceActivity("Bench press", "Fantastic excercise for chest, shoulders and triceps", 6, 8, 25),
//                2, new ResistanceActivity("Cable chest flys", "Build a bulletproof chest", 4, 12, 20),
//                3, new ResistanceActivity("Tricep pulldown", "Great excercise to isolate triceps", 4, 10, 18),
//                4, new ResistanceActivity("Freeweight dips", "Great compound push excercise", 4, 8, 20)));

        return wellbeingProgrammeRepository.save(programme);
    }

//    @GetMapping("/{programmeId}/add-another-test-activities-to-stage")
//    private WellbeingProgramme addAnotherTestActivityToProgrammeStage(@PathVariable String programmeId) {
//        var programme = wellbeingProgrammeRepository.findById(programmeId)
//                .orElseThrow(() -> new IllegalArgumentException("Cannot find programme with ID"));
//
//        var programmeStage = programme.getProgrammeStageMap().get(3);
//        programmeStage.setActivityMap(Map.of(
//                1, new MindfulnessActivity("Mindfulness meditation", "Learn to focus on the present with a 15 minute mindfulness meditation", 15),
//                2, new MindfulnessActivity("More mindfulness meditation", "Practice focusing on your breathing with another 15 minute mindfulness meditation", 15),
//                3, new MindfulnessActivity("Yoga", "Yoga improves strength, balance and flexibility, while helping you relax", 30),
//                4, new MindfulnessActivity("Mindfulness meditation", "Learn to focus on the present with a 20 minute mindfulness meditation", 20)
//        ));
//
//        return wellbeingProgrammeRepository.save(programme);
//    }
//
//    @GetMapping("/{programmeId}/add-third-test-activities-to-stage")
//    private WellbeingProgramme addThirdTestActivityToProgrammeStage(@PathVariable String programmeId) {
//        var programme = wellbeingProgrammeRepository.findById(programmeId)
//                .orElseThrow(() -> new IllegalArgumentException("Cannot find programme with ID"));
//
//        var programmeStage = programme.getProgrammeStageMap().get(5);
//        programmeStage.setActivityMap(Map.of(
//                1, new MindfulnessActivity("Mindfulness meditation", "Learn to focus on the present with a 15 minute mindfulness meditation", 15),
//                2, new MindfulnessActivity("More mindfulness meditation", "Practice focusing on your breathing with another 15 minute mindfulness meditation", 15),
//                3, new MindfulnessActivity("Yoga", "Yoga improves strength, balance and flexibility, while helping you relax", 30),
//                4, new MindfulnessActivity("Mindfulness meditation", "Learn to focus on the present with a 20 minute mindfulness meditation", 20)
//        ));
//
//        return wellbeingProgrammeRepository.save(programme);
//    }

    @GetMapping("/{programmeId}")
    private ResponseEntity<WellbeingProgramme> getProgrammeDetails(@PathVariable String programmeId) throws EntityNotFoundException {
        var programme = wellbeingProgrammeRepository.findById(programmeId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find programme with ID"));
        return ResponseEntity.ok().body(programme);
    }

    @GetMapping("/{programmeId}/get-activities")
    private List<Activity> getActiviesForProgrammeStage(@PathVariable String programmeId) {
        var programme = wellbeingProgrammeRepository.findById(programmeId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find programme with ID"));

        return new ArrayList<>(programme.getProgrammeStageMap().get(1).getActivityMap().values());
    }

}
