package com.movieevent.movienight.entity;

import com.google.api.client.util.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
public class User {
    @Id
    private String email;
    private List<UserEvent> userEvents;
    private  String accessToken;
    private  String refreshToken;

    public Long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Long expiresAt) {
        this.expiresAt = expiresAt;
    }

    private  Long expiresAt;
    private  List<String> freeTime;

    public List<String> getFreeTime() {
        return freeTime;
    }

    public void setFreeTime(List<String> freeTime) {
        this.freeTime = freeTime;
    }

    public List<UserEvent> getUserEvents() {
        return userEvents;
    }
    public void addEvent(UserEvent userEvent){
        userEvents.add(userEvent);
    }
    public void deleteEvent(UserEvent userEvent){
        userEvents.remove(userEvent);
    }

    public void setUserEvents(List<UserEvent> userEvents) {
        this.userEvents = userEvents;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void addFreetime (String freeTimeEvent){
        freeTime.add(freeTimeEvent);
    }
    public User() {
        userEvents =new ArrayList<>();
        freeTime=new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




}
