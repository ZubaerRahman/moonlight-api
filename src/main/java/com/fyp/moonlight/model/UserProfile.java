package com.fyp.moonlight.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;


@Document(collection = "user_profiles")
public class UserProfile {

    @Id
    private String id;

    @Field
    private String userId;

    @Field
    @NotEmpty(message = "Fist name may not be empty")
    @Size(min = 2, message = "First name is too short")
    @Size(max = 64, message = "First name is too long")
    public String firstName;

    @Field
    @NotEmpty(message = "Last name may not be empty")
    @Size(min = 2, message = "Last name is too short")
    @Size(max = 64, message = "Last name is too long")
    public String lastName;

    @Field
    @NotEmpty(message = "Sex must not be empty")
    public String sex;

    @Field
    private Integer versionNo;

    @Field
    private Instant lastUpdatedDatetime;

    @Field
    @NotNull(message = "Height may not be null")
    private Double height;

    @Field
    @NotNull(message = "Weight may not be null")
    private Double weight;

    @Field
    private Double bodyFatPercentage;

    public UserProfile() {
    }

    public UserProfile(String userId, String firstName, String lastName, String sex, Instant lastUpdatedDatetime, Integer versionNo, Double height, Double weight, Double bodyFatPercentage) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.lastUpdatedDatetime = lastUpdatedDatetime;
        this.versionNo = versionNo;
        this.height = height;
        this.weight = weight;
        this.bodyFatPercentage = bodyFatPercentage;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getBodyFatPercentage() {
        return bodyFatPercentage;
    }

    public void setBodyFatPercentage(Double bodyFatPercentage) {
        this.bodyFatPercentage = bodyFatPercentage;
    }

}
