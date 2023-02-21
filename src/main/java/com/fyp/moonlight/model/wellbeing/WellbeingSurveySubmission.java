package com.fyp.moonlight.model.wellbeing;

import com.fyp.moonlight.model.wellbeingProgrammes.ProgrammeEnrolmentSnapshot;
import com.fyp.moonlight.model.wellbeingProgrammes.ProgrammeEnrolmentView;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Document(collection = "wellbeing_survey_submissions")
public class WellbeingSurveySubmission {

    @Id
    private String id;
    private String submittedByUserId;
    private Instant submissionDateTime;
    private WellbeingSurveyType wellbeingSurveyType;
    private Map<Integer, Integer> questionScores;
    private String userComment;
    private ProgrammeEnrolmentSnapshot programmeEnrolmentSnapshot;

    public WellbeingSurveySubmission() {
    }

    public WellbeingSurveySubmission(String submittedByUserId, Instant submissionDateTime, WellbeingSurveyType wellbeingSurveyType, Map<Integer, Integer> questionScores, String userComment) {
        this.submittedByUserId = submittedByUserId;
        this.submissionDateTime = submissionDateTime;
        this.wellbeingSurveyType = wellbeingSurveyType;
        this.questionScores = questionScores;
        this.userComment = userComment;
    }

    public String getId() {
        return id;
    }

    public String getSubmittedByUserId() {
        return submittedByUserId;
    }

    public void setSubmittedByUserId(String submittedByUserId) {
        this.submittedByUserId = submittedByUserId;
    }

    public Instant getSubmissionDateTime() {
        return submissionDateTime;
    }

    public void setSubmissionDateTime(Instant submissionDateTime) {
        this.submissionDateTime = submissionDateTime;
    }

    public WellbeingSurveyType getWellbeingSurveyType() {
        return wellbeingSurveyType;
    }

    public void setWellbeingSurveyType(WellbeingSurveyType wellbeingSurveyType) {
        this.wellbeingSurveyType = wellbeingSurveyType;
    }

    public Map<Integer, Integer> getQuestionScores() {
        return questionScores;
    }

    public void setQuestionScores(Map<Integer, Integer> questionScores) {
        this.questionScores = questionScores;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public ProgrammeEnrolmentSnapshot getProgrammeEnrolmentSnapshot() {
        return programmeEnrolmentSnapshot;
    }

    public void setProgrammeEnrolmentSnapshot(ProgrammeEnrolmentSnapshot programmeEnrolmentSnapshot) {
        this.programmeEnrolmentSnapshot = programmeEnrolmentSnapshot;
    }
}
