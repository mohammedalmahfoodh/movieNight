package com.movie.googlecalendar.repository;

import com.movie.googlecalendar.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface MovieRepository extends MongoRepository<Movie,String> {

}