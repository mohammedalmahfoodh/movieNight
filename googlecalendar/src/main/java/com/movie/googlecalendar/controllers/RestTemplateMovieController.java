package com.movie.googlecalendar.controllers;

import com.movie.googlecalendar.model.ListMovie;
import com.movie.googlecalendar.model.Movie;
import com.movie.googlecalendar.repository.MovieRepository;
import com.movie.googlecalendar.utilities.ResourceNotFoundException;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestClientException;

import org.springframework.web.client.RestTemplate;


import javax.servlet.RequestDispatcher;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

@RestController
public class RestTemplateMovieController {

    RestTemplate restTemplate = new RestTemplate();

    MovieRepository movieRepository;


    public RestTemplateMovieController(MovieRepository movieRepository) {

        this.movieRepository = movieRepository;

    }


    @GetMapping("/template/movie")

    public String getMovies() {

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);


        return restTemplate.exchange("http://www.omdbapi.com/?t=batman&y=2005&typy=movie&apikey=22dcb687",

                HttpMethod.GET, entity, String.class).getBody();

    }


    @GetMapping("/template/movies")

    public Movie getMovieJSON() {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.

                exchange("http://www.omdbapi.com/?t=batman&y=2005&typy=movie&apikey=22dcb687",

                        HttpMethod.GET, entity, String.class);


        Movie movie = new Movie();

        movie = restTemplate.getForObject("http://www.omdbapi.com/?t=batman&y=2005&typy=movie&apikey=22dcb687",

                Movie.class);

        movieRepository.save(movie);


        return movie;

    }


    @RequestMapping("/movie/{imdbID}")

    public Movie getMoviesByTitle(@PathVariable String imdbID) {

        List<Movie> movieList = movieRepository.findAll();

        String url = "http://www.omdbapi.com/?i=" + imdbID + "&typy=movie&apikey=22dcb687";

        //Search for movie in db before fetching it from OMDb API

        for (Movie movieInDB : movieList) {

            if (movieInDB.getImdbID().equalsIgnoreCase(imdbID)) {

                System.out.println("movie founded in database");

                return movieInDB;
            }

        }
        System.out.println("movie not founded in database");
        Movie movie = new Movie();

        RestTemplate restTemplate = new RestTemplate();

        movie = restTemplate.getForObject(url, Movie.class);

        if (!movie.getTitle().equalsIgnoreCase(null)) {
            movieRepository.save(movie);

            return movie;
        }
        return null;



    }


    @RequestMapping("/movie/all/{allMovie}")

    public ListMovie getAll(@PathVariable String allMovie) throws ResourceNotFoundException {

        String url = "http://www.omdbapi.com/?s=" + allMovie + "&typy=movie&apikey=22dcb687";

        RestTemplate restTemplate = new RestTemplate();

        ListMovie listMovie = restTemplate.getForObject(url, ListMovie.class);

        if (listMovie.checkListMovie()) {

            throw new ResourceNotFoundException("There is no such movie name Http status cod 404 not found");


        }


        return listMovie;

    }


}