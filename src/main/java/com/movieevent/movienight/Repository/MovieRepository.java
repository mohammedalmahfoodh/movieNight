package com.movieevent.movienight.Repository;

import com.movieevent.movienight.entity.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface MovieRepository extends MongoRepository <Movie,String> {
}
