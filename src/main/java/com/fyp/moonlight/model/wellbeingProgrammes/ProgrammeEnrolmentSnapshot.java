package com.fyp.moonlight.model.wellbeingProgrammes;

import com.fyp.moonlight.model.wellbeing.WellbeingType;

public class ProgrammeEnrolmentSnapshot {
    private final String enrolmentId;
    private final String programmeId;
    private final String programmeName;
    private final WellbeingType focusedWellbeingType;
    private final EnrolmentStatus enrolmentStatus;
    private final String enrolmentDateTime;
    private final int currentStage;

    public ProgrammeEnrolmentSnapshot(String enrolmentId, String programmeId, String programmeName, WellbeingType focusedWellbeingType, EnrolmentStatus enrolmentStatus, String enrolmentDateTime, int currentStage) {
        this.enrolmentId = enrolmentId;
        this.programmeId = programmeId;
        this.programmeName = programmeName;
        this.focusedWellbeingType = focusedWellbeingType;
        this.enrolmentStatus = enrolmentStatus;
        this.enrolmentDateTime = enrolmentDateTime;
        this.currentStage = currentStage;
    }

    public String getEnrolmentId() {
        return enrolmentId;
    }

    public String getProgrammeId() {
        return programmeId;
    }

    public String getProgrammeName() {
        return programmeName;
    }

    public WellbeingType getFocusedWellbeingType() {
        return focusedWellbeingType;
    }

    public EnrolmentStatus getEnrolmentStatus() {
        return enrolmentStatus;
    }

    public String getEnrolmentDateTime() {
        return enrolmentDateTime;
    }

    public int getCurrentStage() {
        return currentStage;
    }
}
