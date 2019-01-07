package com.movieevent.movienight.Repository;

import com.movieevent.movienight.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends MongoRepository<User,String> {

    public User findByEmail(String email);
}
