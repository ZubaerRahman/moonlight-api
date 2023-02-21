package com.fyp.moonlight.model.wellbeing;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.util.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum WellbeingSurveyType {

    WEMWBS_SURVEY(
            "Warwick–Edinburgh Mental Wellbeing Scale Survey",
            WellbeingType.OVERALL,
            "Survey that aims to measure the current state of the subject's general wellbeing.",
            List.of(
                    "I’ve been feeling optimistic about the future",
                    "I’ve been feeling useful",
                    "I’ve been feeling relaxed",
                    "I’ve been feeling interested in other people",
                    "I’ve had energy to spare", "I’ve been dealing with problems well",
                    "I’ve been thinking clearly", "I’ve been feeling good about myself",
                    "I’ve been feeling close to other people",
                    "I’ve been feeling confident",
                    "I’ve been able to make up my own mind about things",
                    "I’ve been feeling loved ",
                    "I’ve been interested in new things" ,
                    "I’ve been feeling cheerful"
            ),
            70
    ),

    SOCIAL_WB_SURVEY(
            "Social Wellbeing Survey",
            WellbeingType.SOCIAL,
            "Survey that aims to measure the current state of the subject's social wellbeing.",
            List.of(
                    "I’ve been feeling optimistic about the future",
                    "I’ve been feeling useful",
                    "I’ve been feeling relaxed",
                    "I’ve been feeling interested in other people",
                    "I’ve had energy to spare", "I’ve been dealing with problems well",
                    "I’ve been thinking clearly", "I’ve been feeling good about myself",
                    "I’ve been feeling close to other people",
                    "I’ve been feeling confident",
                    "I’ve been able to make up my own mind about things",
                    "I’ve been feeling loved ",
                    "I’ve been interested in new things" ,
                    "I’ve been feeling cheerful"
            ),
            100
    ),

    EMOTIONAL_WB_SURVEY(
            "Emotional Wellbeing Survey",
            WellbeingType.EMOTIONAL,
            "Survey that aims to measure the current state of the subject's emotional wellbeing.",
            List.of(
                    "I’ve been feeling optimistic about the future",
                    "I’ve been feeling useful",
                    "I’ve been feeling relaxed",
                    "I’ve been feeling interested in other people",
                    "I’ve had energy to spare", "I’ve been dealing with problems well",
                    "I’ve been thinking clearly", "I’ve been feeling good about myself",
                    "I’ve been feeling close to other people",
                    "I’ve been feeling confident",
                    "I’ve been able to make up my own mind about things",
                    "I’ve been feeling loved ",
                    "I’ve been interested in new things" ,
                    "I’ve been feeling cheerful"
            ),
            100
    ),

    SPIRITUAL_WB_SURVEY(
            "Spiritual Wellbeing Survey",
            WellbeingType.SPIRITUAL,
            "Survey that aims to measure the current state of the subject's spiritual wellbeing.",
            List.of(
                    "I’ve been feeling optimistic about the future",
                    "I’ve been feeling useful",
                    "I’ve been feeling relaxed",
                    "I’ve been feeling interested in other people",
                    "I’ve had energy to spare", "I’ve been dealing with problems well",
                    "I’ve been thinking clearly", "I’ve been feeling good about myself",
                    "I’ve been feeling close to other people",
                    "I’ve been feeling confident",
                    "I’ve been able to make up my own mind about things",
                    "I’ve been feeling loved ",
                    "I’ve been interested in new things" ,
                    "I’ve been feeling cheerful"
            ),
            100
    ),

    INTELLECTUAL_WB_SURVEY(
            "Intellectual Wellbeing Survey",
            WellbeingType.INTELLECTUAL,
            "Survey that aims to measure the current state of the subject's intellectual wellbeing.",
            List.of(
                    "I’ve been feeling optimistic about the future",
                    "I’ve been feeling useful",
                    "I’ve been feeling relaxed",
                    "I’ve been feeling interested in other people",
                    "I’ve had energy to spare", "I’ve been dealing with problems well",
                    "I’ve been thinking clearly", "I’ve been feeling good about myself",
                    "I’ve been feeling close to other people",
                    "I’ve been feeling confident",
                    "I’ve been able to make up my own mind about things",
                    "I’ve been feeling loved ",
                    "I’ve been interested in new things" ,
                    "I’ve been feeling cheerful"
            ),
            100
    ),

    PHYSICAL_WB_SURVEY(
            "Physical Wellbeing Survey",
            WellbeingType.PHYSICAL,
            "Survey that aims to measure the current state of the subject's physical wellbeing.",
            List.of(
                    "I’ve been feeling optimistic about the future",
                    "I’ve been feeling useful",
                    "I’ve been feeling relaxed",
                    "I’ve been feeling interested in other people",
                    "I’ve had energy to spare", "I’ve been dealing with problems well",
                    "I’ve been thinking clearly", "I’ve been feeling good about myself",
                    "I’ve been feeling close to other people",
                    "I’ve been feeling confident",
                    "I’ve been able to make up my own mind about things",
                    "I’ve been feeling loved ",
                    "I’ve been interested in new things" ,
                    "I’ve been feeling cheerful"
            ),
            100
    );

    private final String name;
    private final WellbeingType wellbeingType;
    private final String description;
    private final List<String> questions;
    private final Integer maxScore;


    WellbeingSurveyType(String name, WellbeingType wellbeingType, String description, List<String> questions, Integer maxScore) {
        this.name = name;
        this.wellbeingType = wellbeingType;
        this.description = description;
        this.questions = questions;
        this.maxScore = maxScore;
    }

    public String getName() {
        return name;
    }

    public WellbeingType getWellbeingType() {
        return wellbeingType;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public static Map<String, String> getEnumNameMap() {
        return Arrays.stream(WellbeingSurveyType.values())
                .collect(Collectors.toMap(WellbeingSurveyType::name, WellbeingSurveyType::getName));
    }

}
