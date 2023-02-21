package com.fyp.moonlight.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document(collection = "users")
public class User {

    @Id
    public String id;

    @Field
    public String email;

    @Field
    public String password;

    @Field
    private Instant joinDatetime;

    public User() {
    }

    public User(String email, String password, Instant joinDatetime) {
        this.email = email;
        this.password = password;
        this.joinDatetime = joinDatetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getJoinDatetime() {
        return joinDatetime;
    }

    public void setJoinDatetime(Instant joinDatetime) {
        this.joinDatetime = joinDatetime;
    }
}