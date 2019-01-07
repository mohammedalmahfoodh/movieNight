package com.movie.googlecalendar.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Document(collection = "user")
public class User {
@Id
   private String email;
   private List<String> eventsDates ;

    public User() {
        eventsDates=new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getEventsDates() {
        return eventsDates;
    }

    public void setEventsDates(List<String> eventsDates) {
        this.eventsDates = eventsDates;
    }
}
