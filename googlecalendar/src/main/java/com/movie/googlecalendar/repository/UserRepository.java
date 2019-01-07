package com.movie.googlecalendar.repository;

import com.movie.googlecalendar.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {

}
