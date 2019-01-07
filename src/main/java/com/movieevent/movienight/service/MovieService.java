package com.movieevent.movienight.service;

import com.movieevent.movienight.Repository.MovieRepository;
import com.movieevent.movienight.entity.ListMovie;
import com.movieevent.movienight.entity.Movie;
import com.movieevent.movienight.utilities.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    @Autowired
   private MovieRepository movieRepository;

    RestTemplate restTemplate = new RestTemplate();

    public Movie getMoviesById( String imdbID) {

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

    public ListMovie getAll( String allMovie) throws ResourceNotFoundException {
        String url = "http://www.omdbapi.com/?s=" + allMovie + "&typy=movie&apikey=22dcb687";

        ListMovie listMovie = restTemplate.getForObject(url, ListMovie.class);

        if (listMovie.checkListMovie()) {

            throw new ResourceNotFoundException("There is no such movie name Http status cod 404 not found");


        }


        return listMovie;

    }

    public List<String> getMoviesNames( String allMovie) throws ResourceNotFoundException {
        String url = "http://www.omdbapi.com/?s=" + allMovie + "&typy=movie&apikey=22dcb687";

        ListMovie listMovie=new ListMovie() ;


        List<String>moviesNames=new ArrayList<>();
       if (allMovie.length()>=3){
           listMovie= restTemplate.getForObject(url, ListMovie.class);
           for (Movie movie:listMovie.getSearch() ) {
               moviesNames.add(movie.getTitle());
           }
       }
      //  if (listMovie.checkListMovie()) {

       //     throw new ResourceNotFoundException("There is no such movie name Http status cod 404 not found");

      //  }


        return moviesNames;

    }








}
