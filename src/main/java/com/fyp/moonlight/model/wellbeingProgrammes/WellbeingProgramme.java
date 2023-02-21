package com.fyp.moonlight.model.wellbeingProgrammes;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "wellbeing_programmes")
public class WellbeingProgramme {

    @Id
    private String id;

    private String name;

    private String description;

    private Integer durationInWeeks;

    private Map<Integer, ProgrammeStage> programmeStageMap;

    public WellbeingProgramme() {};

    public WellbeingProgramme(String name, String description, Integer durationInWeeks) {
        this.name = name;
        this.description = description;
        this.durationInWeeks = durationInWeeks;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationInWeeks() {
        return durationInWeeks;
    }

    public void setDurationInWeeks(Integer durationInWeeks) {
        this.durationInWeeks = durationInWeeks;
    }

    public Map<Integer, ProgrammeStage> getProgrammeStageMap() {
        return programmeStageMap;
    }

    public void setProgrammeStageMap(Map<Integer, ProgrammeStage> programmeStageMap) {
        this.programmeStageMap = programmeStageMap;
    }
}
