package com.fyp.moonlight.model.wellbeingProgrammes;

import java.util.Map;

public class ProgrammeStage {

    private String name;

    private Map<Integer, Activity> activityMap;

    public ProgrammeStage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, Activity> getActivityMap() {
        return activityMap;
    }

    public void setActivityMap(Map<Integer, Activity> activityMap) {
        this.activityMap = activityMap;
    }
}
