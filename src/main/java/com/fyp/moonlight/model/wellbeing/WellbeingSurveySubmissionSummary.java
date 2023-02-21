package com.fyp.moonlight.model.wellbeing;

import java.time.Instant;

public class WellbeingSurveySubmissionSummary {

    private final String submissionId;
    private final String submittedByUserId;
    private final String submissionDateTime;
    private final WellbeingSurveyType wellbeingSurveyType;
    private final Integer surveyScore;

    public WellbeingSurveySubmissionSummary(String submissionId, String submittedByUserId, String submissionDateTime, WellbeingSurveyType wellbeingSurveyType, Integer surveyScore) {
        this.submissionId = submissionId;
        this.submittedByUserId = submittedByUserId;
        this.submissionDateTime = submissionDateTime;
        this.wellbeingSurveyType = wellbeingSurveyType;
        this.surveyScore = surveyScore;
    }

    public String getSubmissionId() {
        return submissionId;
    }

    public String getSubmittedByUserId() {
        return submittedByUserId;
    }

    public String getSubmissionDateTime() {
        return submissionDateTime;
    }

    public WellbeingSurveyType getWellbeingSurveyType() {
        return wellbeingSurveyType;
    }

    public Integer getSurveyScore() {
        return surveyScore;
    }
}
