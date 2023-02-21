package com.fyp.moonlight.util;

import com.fyp.moonlight.model.wellbeing.WellbeingSurveySubmission;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class WellbeingUtil {

    public static Integer getWellbeingSurveySubmissionScore(WellbeingSurveySubmission wellbeingSurveySubmission) {
        return wellbeingSurveySubmission.getQuestionScores().values().stream()
                .reduce(0, Integer::sum);
    }

    public static String getDateTimeStringFromInstant(Instant instant) {
         var formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                .withLocale( Locale.UK )
                .withZone( ZoneId.systemDefault() );
         return formatter.format(instant);
    }

}
