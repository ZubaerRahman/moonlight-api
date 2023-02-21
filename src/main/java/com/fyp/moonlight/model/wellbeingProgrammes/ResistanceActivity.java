package com.fyp.moonlight.model.wellbeingProgrammes;

public class ResistanceActivity implements Activity {

    private ActivityType activityType;

    private String activityName;

    private String activityDescription;

    private Integer sets;

    private Integer reps;

    private Integer duration;

    public ResistanceActivity(String activityName, String activityDescription, Integer sets, Integer reps, Integer duration) {
        this.activityType = ActivityType.RESISTANCE;
        this.activityName = activityName;
        this.activityDescription = activityDescription;
        this.sets = sets;
        this.reps = reps;
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

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
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
