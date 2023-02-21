package com.fyp.moonlight.model.wellbeing;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;


@Document(collection = "user_wellbeing_scores")
public class UserWellbeingStats {

    @Id
    private String id;
    private String userId;
    private Integer versionNo;
    private Instant lastUpdatedDatetime;
    private Map<WellbeingType, Integer> wellbeingTypeScoreMap;

    public UserWellbeingStats() {
    }

    public UserWellbeingStats(String userId, Integer versionNo, Instant lastUpdatedDatetime, Map<WellbeingType, Integer> wellbeingTypeScoreMap) {
        this.userId = userId;
        this.versionNo = versionNo;
        this.lastUpdatedDatetime = lastUpdatedDatetime;
        this.wellbeingTypeScoreMap = wellbeingTypeScoreMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getLastUpdatedDatetime() {
        return lastUpdatedDatetime;
    }

    public void setLastUpdatedDatetime(Instant lastUpdatedDatetime) {
        this.lastUpdatedDatetime = lastUpdatedDatetime;
    }

    public Integer getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Integer versionNo) {
        this.versionNo = versionNo;
    }

    public Map<WellbeingType, Integer> getWellbeingTypeScoreMap() {
        return wellbeingTypeScoreMap;
    }

    public void setWellbeingTypeScoreMap(Map<WellbeingType, Integer> wellbeingTypeScoreMap) {
        this.wellbeingTypeScoreMap = wellbeingTypeScoreMap;
    }

}
