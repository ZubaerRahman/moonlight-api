package com.fyp.moonlight.controller;

import com.fyp.moonlight.model.wellbeingProgrammes.Activity;
import com.fyp.moonlight.model.wellbeingProgrammes.ResistanceActivity;
import com.fyp.moonlight.model.wellbeingProgrammes.WellbeingProgramme;
import com.fyp.moonlight.model.wellbeingProgrammes.ProgrammeStage;
import com.fyp.moonlight.repository.WellbeingProgrammeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/programmes/{programmeId}/stages")
public class ProgrammeStageController {

    @Autowired
    private WellbeingProgrammeRepository wellbeingProgrammeRepository;

    @PostMapping("/add-stage")
    private ProgrammeStage addStageToProgramme(@PathVariable String programmeId,
                                               @RequestBody ProgrammeStage programmeStage) {
        var programme = getProgrammeOrThrow(programmeId);
        var currentStages = programme.getProgrammeStageMap();
        var latestStageKey = currentStages.keySet().stream().max(Comparator.comparingInt(Integer::intValue)).orElse(0);
        var latestStage = currentStages.getOrDefault(latestStageKey, null);

        if (latestStage != null) {
            currentStages.put(latestStageKey + 1, programmeStage);
        } else {
            currentStages.put(1, programmeStage);
        }

        wellbeingProgrammeRepository.save(programme);
        return programmeStage;
    }

//    @GetMapping("/add-test-stage/cardio")
//    public WellbeingProgramme addResistanceTestStageToProgramme(@PathVariable String programmeId) {
//        var programme = getProgrammeOrThrow(programmeId);
//        var currentStages = programme.getProgrammeStageMap();
//        var latestStageKey = currentStages.keySet().stream().max(Comparator.comparingInt(Integer::intValue)).orElse(0);
//        var latestStage = currentStages.getOrDefault(latestStageKey, null);
//
//        var newTestStage = new ProgrammeStage("Test stage name", Map.of(1, new ResistanceActivity("Bench press", "Fantastic excercise for chest, shoulders and triceps", 6, 8, 25)));
//
//        if (latestStage != null) {
//            currentStages.put(latestStageKey + 1, newTestStage);
//        } else {
//            currentStages.put(1, newTestStage);
//        }
//
//        return(wellbeingProgrammeRepository.save(programme));
//    }
//
//    @GetMapping("/add-test-stage/mind")
//    public WellbeingProgramme addMindfulTestStageToProgramme(@PathVariable String programmeId) {
//        var programme = getProgrammeOrThrow(programmeId);
//        var currentStages = programme.getProgrammeStageMap();
//        var latestStageKey = currentStages.keySet().stream().max(Comparator.comparingInt(Integer::intValue)).orElse(0);
//        var latestStage = currentStages.getOrDefault(latestStageKey, null);
//
//        var newTestStage = new ProgrammeStage("Test stage name", Map.of(
//                1, new ResistanceActivity("Bench press", "Fantastic excercise for chest, shoulders and triceps", 6, 8, 25),
//                2, new ResistanceActivity("Cable chest flys", "Build a bulletproof chest", 4, 12, 20),
//                3, new ResistanceActivity("Tricep pulldown", "Great excercise to isolate triceps", 4, 10, 18),
//                4, new ResistanceActivity("Freeweight dips", "Great compound push excercise", 4, 8, 20)
//        ));
//        if (latestStage != null) {
//            currentStages.put(latestStageKey + 1, newTestStage);
//        } else {
//            currentStages.put(1, newTestStage);
//        }
//
//        return(wellbeingProgrammeRepository.save(programme));
//    }
//
//    @GetMapping("/add-test-stage/resistance")
//    public WellbeingProgramme addCardioTestStageToProgramme(@PathVariable String programmeId) {
//        var programme = getProgrammeOrThrow(programmeId);
//        var currentStages = programme.getProgrammeStageMap();
//        var latestStageKey = currentStages.keySet().stream().max(Comparator.comparingInt(Integer::intValue)).orElse(0);
//        var latestStage = currentStages.getOrDefault(latestStageKey, null);
//        var activityMap = new LinkedHashMap<Integer, Activity>();
//        activityMap.put(1, new ResistanceActivity("Bench press", "Fantastic excercise for chest, shoulders and triceps", 6, 8, 25));
//        activityMap.put(2, new ResistanceActivity("Cable chest flys", "Build a bulletproof chest", 4, 12, 20));
//        activityMap.put(3, new ResistanceActivity("Tricep pulldown", "Great excercise to isolate triceps", 4, 10, 18));
//        activityMap.put(4, new ResistanceActivity("Tricep pulldown", "Great excercise to isolate triceps", 4, 10, 18));
//
//        var newTestStage = new ProgrammeStage("Test stage name", activityMap);
//
//        if (latestStage != null) {
//            currentStages.put(latestStageKey + 1, newTestStage);
//        } else {
//            currentStages.put(1, newTestStage);
//        }
//
//        return(wellbeingProgrammeRepository.save(programme));
//    }

    @PostMapping("/{stage-number}/add-activity")
    private WellbeingProgramme addActivityToStage(@PathVariable String programmeId,
                                                  @PathVariable Integer stageNumber,
                                                  @RequestBody Activity activity) {
        var programme = getProgrammeOrThrow(programmeId);
        var currentStages = programme.getProgrammeStageMap();
        var stage = Optional.ofNullable(currentStages.get(stageNumber))
                .orElseThrow(() -> new NoSuchElementException("Stage " + stageNumber + " not found in programme with id " + programmeId));

        var latestActivityNumber = stage.getActivityMap().keySet().stream()
                .max(Comparator.comparingInt(Integer::intValue)).orElse(0);
        stage.getActivityMap().put(latestActivityNumber + 1, activity);

        wellbeingProgrammeRepository.save(programme);
        return programme;
    }

    private WellbeingProgramme getProgrammeOrThrow(String programmeId) {
        return wellbeingProgrammeRepository.findById(programmeId)
                .orElseThrow(() -> new IllegalArgumentException("Could not find programme with id " + programmeId));
    }

}
