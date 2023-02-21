package com.fyp.moonlight.repository;

import com.fyp.moonlight.model.ProgrammeEnrolment;
import com.fyp.moonlight.model.wellbeingProgrammes.EnrolmentStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgrammeEnrolmentRepository extends MongoRepository<ProgrammeEnrolment, String> {

    public Optional<ProgrammeEnrolment> findByUserIdAndProgrammeId(String userId, String programmeId);

    public Optional<ProgrammeEnrolment> findByUserIdAndEnrolmentStatus(String userId, EnrolmentStatus enrolmentStatus);

}
