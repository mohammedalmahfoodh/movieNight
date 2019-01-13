package com.movieevent.movienight.controller;

import com.movieevent.movienight.entity.ListMovie;
import com.movieevent.movienight.entity.Movie;
import com.movieevent.movienight.service.MovieService;
import com.movieevent.movienight.utilities.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
public class RestTemplateMovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/movie/{imdbID}")
    public Movie getMoviesByTitle(@PathVariable String imdbID) {


        return movieService.getMoviesById(imdbID);
    }

    @GetMapping("/movie/all/{movieTitle}")
    public ListMovie getByTitle(@PathVariable String movieTitle) {

        return movieService.getAll(movieTitle);
    }

    @GetMapping("/movie/names/{movieTitle}")
    public List<String> getMoviesNames(@PathVariable String movieTitle) {

        return movieService.getMoviesNames(movieTitle);
    }



}
