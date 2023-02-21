package com.fyp.moonlight.model;

import com.fyp.moonlight.model.wellbeing.WellbeingType;
import com.fyp.moonlight.model.wellbeingProgrammes.EnrolmentStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "programmme_enrolments")
public class ProgrammeEnrolment {

    @Id
    private String id;

    private String userId;

    private String programmeId;

    private WellbeingType focusedWellbeingType;

    private Instant enrolmentTimestamp;

    private Instant terminationTimestamp;

    private Integer currentStage;

    private EnrolmentStatus enrolmentStatus;

    public ProgrammeEnrolment(String userId, String programmeId, WellbeingType focusedWellbeingType, Instant enrolmentTimestamp, Integer currentStage, EnrolmentStatus enrolmentStatus) {
        this.userId = userId;
        this.programmeId = programmeId;
        this.focusedWellbeingType = focusedWellbeingType;
        this.enrolmentTimestamp = enrolmentTimestamp;
        this.currentStage = currentStage;
        this.enrolmentStatus = enrolmentStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProgrammeId() {
        return programmeId;
    }

    public void setProgrammeId(String programmeId) {
        this.programmeId = programmeId;
    }

    public WellbeingType getFocusedWellbeingType() {
        return focusedWellbeingType;
    }

    public void setFocusedWellbeingType(WellbeingType focusedWellbeingType) {
        this.focusedWellbeingType = focusedWellbeingType;
    }

    public Instant getEnrolmentTimestamp() {
        return enrolmentTimestamp;
    }

    public void setEnrolmentTimestamp(Instant enrolmentTimestamp) {
        this.enrolmentTimestamp = enrolmentTimestamp;
    }

    public Instant getTerminationTimestamp() {
        return terminationTimestamp;
    }

    public void setTerminationTimestamp(Instant terminationTimestamp) {
        this.terminationTimestamp = terminationTimestamp;
    }

    public Integer getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Integer currentStage) {
        this.currentStage = currentStage;
    }

    public EnrolmentStatus getEnrolmentStatus() {
        return enrolmentStatus;
    }

    public void setEnrolmentStatus(EnrolmentStatus enrolmentStatus) {
        this.enrolmentStatus = enrolmentStatus;
    }
}


