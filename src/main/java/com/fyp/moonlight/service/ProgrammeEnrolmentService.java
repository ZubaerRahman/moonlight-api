package com.fyp.moonlight.service;

import com.fyp.moonlight.model.ProgrammeEnrolment;
import com.fyp.moonlight.model.wellbeingProgrammes.ProgrammeEnrolmentSnapshot;
import com.fyp.moonlight.model.wellbeingProgrammes.EnrolmentStatus;
import com.fyp.moonlight.model.wellbeingProgrammes.ProgrammeEnrolmentView;
import com.fyp.moonlight.repository.ProgrammeEnrolmentRepository;
import com.fyp.moonlight.repository.WellbeingProgrammeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Optional;

@Service
public class ProgrammeEnrolmentService {

    @Autowired
    private ProgrammeEnrolmentRepository programmeEnrolmentRepository;

    @Autowired
    private WellbeingProgrammeRepository wellbeingProgrammeRepository;

    public Optional<ProgrammeEnrolmentSnapshot> getProgrammeEnrolmentSnapshot(ProgrammeEnrolment programmeEnrolment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                .withLocale( Locale.UK )
                .withZone( ZoneId.systemDefault() );

        var wellbeingProgrammeOptional = wellbeingProgrammeRepository.findById(programmeEnrolment.getProgrammeId());
        return wellbeingProgrammeOptional.map(wellbeingProgramme -> new ProgrammeEnrolmentSnapshot(
                programmeEnrolment.getId(),
                wellbeingProgramme.getId(),
                wellbeingProgramme.getName(),
                programmeEnrolment.getFocusedWellbeingType(),
                programmeEnrolment.getEnrolmentStatus(),
                formatter.format(programmeEnrolment.getEnrolmentTimestamp()),
                programmeEnrolment.getCurrentStage()
        ));
    }

    public Optional<ProgrammeEnrolmentView> getProgrammeEnrolmentView(ProgrammeEnrolment programmeEnrolment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                .withLocale( Locale.UK )
                .withZone( ZoneId.systemDefault() );

        var wellbeingProgrammeOptional = wellbeingProgrammeRepository.findById(programmeEnrolment.getProgrammeId());
        return wellbeingProgrammeOptional.map(wellbeingProgramme -> new ProgrammeEnrolmentView(
                programmeEnrolment.getId(),
                wellbeingProgramme.getId(),
                wellbeingProgramme.getName(),
                programmeEnrolment.getFocusedWellbeingType(),
                programmeEnrolment.getEnrolmentStatus(),
                formatter.format(programmeEnrolment.getEnrolmentTimestamp()),
                programmeEnrolment.getEnrolmentStatus() != EnrolmentStatus.ACTIVE ? formatter.format(programmeEnrolment.getTerminationTimestamp()) : null
        ));
    }

    public Optional<ProgrammeEnrolment> getCurrentProgrammeEnrolmentForUser(String userId) {
        return programmeEnrolmentRepository.findByUserIdAndEnrolmentStatus(userId, EnrolmentStatus.ACTIVE);
    }
}
