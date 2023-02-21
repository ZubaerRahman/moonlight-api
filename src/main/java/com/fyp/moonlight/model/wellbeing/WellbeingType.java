package com.fyp.moonlight.model.wellbeing;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum WellbeingType {

    OVERALL,
    SOCIAL,
    EMOTIONAL,
    SPIRITUAL,
    INTELLECTUAL,
    PHYSICAL

}
