package com.movie.googlecalendar.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;



@Document(collection = "movie")

@JsonIgnoreProperties(ignoreUnknown = true)

public class Movie  {



    @Id
    private String imdbID;

    @JsonProperty("Title")

    private String Title;

    private String imdbRating;

    @JsonProperty("Year")

    private String Year;

    @JsonProperty("Poster")

    private String Poster;



    public Movie() {

    }



    public String getImdbID() {

        return imdbID;

    }



    public void setImdbID(String imdbID) {

        this.imdbID = imdbID;

    }



    public String getTitle() {

        return Title;

    }



    public void setTitle(String title) {

        title = title;

    }



    public String getImdbRating() {

        return imdbRating;

    }



    public void setImdbRating(String imdbRating) {

        this.imdbRating = imdbRating;

    }



    public String getYear() {

        return Year;

    }



    public void setYear(String year) {

        year = year;

    }



    public String getPoster() {

        return Poster;

    }



    public void setPoster(String poster) {

        poster = poster;

    }

}