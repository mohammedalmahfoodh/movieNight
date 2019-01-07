package com.movieevent.movienight;

import com.google.api.client.util.DateTime;
import com.movieevent.movienight.Repository.UserRepository;
import com.movieevent.movienight.entity.User;
import com.movieevent.movienight.entity.UserEvent;
import com.movieevent.movienight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EnableMongoAuditing
@SpringBootApplication
public class MovienightApplication {


    public static void main(String[] args) {
        SpringApplication.run(MovienightApplication.class, args);






    }

}

