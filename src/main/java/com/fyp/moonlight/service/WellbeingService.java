package com.fyp.moonlight.service;

import com.fyp.moonlight.model.ProgrammeEnrolment;
import com.fyp.moonlight.model.wellbeing.UserWellbeingStats;
import com.fyp.moonlight.model.wellbeing.WellbeingSurveySubmission;
import com.fyp.moonlight.model.wellbeing.WellbeingSurveySubmissionSummary;
import com.fyp.moonlight.model.wellbeing.WellbeingType;
import com.fyp.moonlight.repository.UserWellbeingStatsRepository;
import com.fyp.moonlight.repository.WellbeingSurveyRepository;
import com.fyp.moonlight.util.WellbeingUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WellbeingService {

    @Autowired
    public WellbeingSurveyRepository wellbeingSurveyRepository;

    @Autowired
    public UserWellbeingStatsRepository userWellbeingStatsRepository;

    public void saveSurveyAndUpdateStats(WellbeingSurveySubmission wellbeingSurveySubmission) {
        wellbeingSurveyRepository.save(wellbeingSurveySubmission);
        updateUserWellbeingStats(wellbeingSurveySubmission);
    }

    public Optional<UserWellbeingStats> findLatestUserWellbeingStats(String userId) {
        return userWellbeingStatsRepository.findTopByUserIdOrderByVersionNoDesc(userId);
    }

    private void updateUserWellbeingStats(WellbeingSurveySubmission wellbeingSurveySubmission) {
        var submissionScore = wellbeingSurveySubmission.getQuestionScores().values().stream()
                .reduce(0, Integer::sum);

        var latestStatsOptional = findLatestUserWellbeingStats(wellbeingSurveySubmission.getSubmittedByUserId());
        var latestStatsVersion = latestStatsOptional.map(UserWellbeingStats::getVersionNo).orElse(0);
        var latestWellbeingScoresMap = latestStatsOptional.map(UserWellbeingStats::getWellbeingTypeScoreMap).orElse(new HashMap<>());
        latestWellbeingScoresMap.put(wellbeingSurveySubmission.getWellbeingSurveyType().getWellbeingType(), submissionScore);

        var newWellbeingStats = new UserWellbeingStats(wellbeingSurveySubmission.getSubmittedByUserId(),
                latestStatsVersion + 1, Instant.now(), latestWellbeingScoresMap);
        userWellbeingStatsRepository.save(newWellbeingStats);
    }

    public List<WellbeingSurveySubmission> getSurveySubmissionsRelatedToProgrammeEnrolmentByUser(String userId) {
        return wellbeingSurveyRepository.findBySubmittedByUserIdAndProgrammeEnrolmentSnapshotIsNotNull(userId);
    }

    public boolean hasUserSubmittedSurveyForCurrentProgrammeStage(ProgrammeEnrolment currentEnrolment) {
        var surveySubmissionsForUser = wellbeingSurveyRepository
                .findBySubmittedByUserIdAndProgrammeEnrolmentSnapshotIsNotNull(currentEnrolment.getUserId());
        return surveySubmissionsForUser.stream()
                .anyMatch(wellbeingSurveySubmission ->
                        wellbeingSurveySubmission.getProgrammeEnrolmentSnapshot().getEnrolmentId().equals(currentEnrolment.getId())
                                && Integer.valueOf(wellbeingSurveySubmission.getProgrammeEnrolmentSnapshot().getCurrentStage()).equals(currentEnrolment.getCurrentStage()));
    }

    public Map<Object, Collection<Object>> getWellbeingTypeToSubmissionsMapForUser(String userId) {
        var allSubmissionsForUser = wellbeingSurveyRepository.findBySubmittedByUserId(userId).stream()
                .sorted(Comparator.comparing(WellbeingSurveySubmission::getSubmissionDateTime));

        var wellbeingTypeToSubmissionSummaryMultiMap = ArrayListMultimap.create();
        allSubmissionsForUser.forEach(submission -> wellbeingTypeToSubmissionSummaryMultiMap.put(
                submission.getWellbeingSurveyType().getWellbeingType(),
                new WellbeingSurveySubmissionSummary(submission.getId(), submission.getSubmittedByUserId(),
                        WellbeingUtil.getDateTimeStringFromInstant(submission.getSubmissionDateTime()), submission.getWellbeingSurveyType(),
                        WellbeingUtil.getWellbeingSurveySubmissionScore(submission))));

        return wellbeingTypeToSubmissionSummaryMultiMap.asMap();
    }

    public Map<Object, Collection<Object>> getProgrammeEnrolmentToSubmissionsMapForUser(String userId) {
        var allProgrammeSubmissionsForUser = getSurveySubmissionsRelatedToProgrammeEnrolmentByUser(userId);

        var programmeEnrolmentToSubmissionsMultiMap = ArrayListMultimap.create();
        allProgrammeSubmissionsForUser.forEach(submission -> programmeEnrolmentToSubmissionsMultiMap.put(
                submission.getProgrammeEnrolmentSnapshot().getEnrolmentId(),
                new WellbeingSurveySubmissionSummary(submission.getId(), submission.getSubmittedByUserId(),
                        WellbeingUtil.getDateTimeStringFromInstant(submission.getSubmissionDateTime()), submission.getWellbeingSurveyType(),
                        WellbeingUtil.getWellbeingSurveySubmissionScore(submission))));

        return programmeEnrolmentToSubmissionsMultiMap.asMap();
    }

}
