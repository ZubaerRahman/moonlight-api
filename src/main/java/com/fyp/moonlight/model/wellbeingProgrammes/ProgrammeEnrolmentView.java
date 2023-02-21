package com.fyp.moonlight.model.wellbeingProgrammes;

import com.fyp.moonlight.model.wellbeing.WellbeingType;

public class ProgrammeEnrolmentView {
    private final String enrolmentId;
    private final String programmeId;
    private final String programmeName;
    private final WellbeingType focusedWellbeingType;
    private final EnrolmentStatus enrolmentStatus;
    private final String enrolmentDateTime;
    private final String terminationDateTime;

    public ProgrammeEnrolmentView(String enrolmentId, String programmeId, String programmeName, WellbeingType focusedWellbeingType, EnrolmentStatus enrolmentStatus, String enrolmentDateTime, String terminationDateTime) {
        this.enrolmentId = enrolmentId;
        this.programmeId = programmeId;
        this.programmeName = programmeName;
        this.focusedWellbeingType = focusedWellbeingType;
        this.enrolmentStatus = enrolmentStatus;
        this.enrolmentDateTime = enrolmentDateTime;
        this.terminationDateTime = terminationDateTime;
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

    public String getTerminationDateTime() {
        return terminationDateTime;
    }
}
