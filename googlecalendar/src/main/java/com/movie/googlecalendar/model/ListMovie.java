package com.movie.googlecalendar.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public class ListMovie {

    @JsonProperty("Search")

    private List<Movie> Search;



    public boolean checkListMovie(){

        if (Search.isEmpty())

            return true;



        return false;

    }



    public ListMovie() {

        Search =new ArrayList<>();

    }



    public List<Movie> getSearch() {

        return Search;

    }



    public void setSearch(List<Movie> search) {

        this.Search = search;

    }

}