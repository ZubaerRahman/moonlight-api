package com.fyp.moonlight.repository;

import com.fyp.moonlight.model.wellbeing.WellbeingSurveySubmission;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellbeingSurveyRepository extends MongoRepository<WellbeingSurveySubmission, String> {

    public List<WellbeingSurveySubmission> findBySubmittedByUserId(String userId);

    public List<WellbeingSurveySubmission> findBySubmittedByUserIdAndProgrammeEnrolmentSnapshotIsNotNull(String userId);

}
