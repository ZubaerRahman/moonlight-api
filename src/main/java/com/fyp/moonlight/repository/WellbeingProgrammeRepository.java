package com.fyp.moonlight.repository;

import com.fyp.moonlight.model.wellbeingProgrammes.WellbeingProgramme;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WellbeingProgrammeRepository extends MongoRepository<WellbeingProgramme, String> {

    public Optional<WellbeingProgramme> findByName(String name);

}
