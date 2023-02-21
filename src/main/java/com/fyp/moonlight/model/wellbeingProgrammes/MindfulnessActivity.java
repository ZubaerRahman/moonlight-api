package com.fyp.moonlight.model.wellbeingProgrammes;

public class MindfulnessActivity implements Activity {

    private ActivityType activityType;

    private String activityName;

    private String activityDescription;

    private Integer duration;

    public MindfulnessActivity(String activityName, String activityDescription, Integer duration) {
        this.activityType = ActivityType.MINDFULNESS;
        this.activityName = activityName;
        this.activityDescription = activityDescription;
        this.duration = duration;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }
}
