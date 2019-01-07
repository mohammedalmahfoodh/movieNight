package com.movie.googlecalendar.controllers;

import com.movie.googlecalendar.model.User;
import com.movie.googlecalendar.repository.UserRepository;
import com.movie.googlecalendar.utilities.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @RequestMapping(method = RequestMethod.POST, value = "/user/saveuser")
    public String saveUser(@RequestBody User user) {
        if (user != null) {
            userRepository.save(user);
            return "user saved successfully";
        }
        return "user not saved";
    }


    @RequestMapping("/user/{email}")
    public User getMoviesByTitle(@PathVariable String email) throws ResourceNotFoundException {
        List<User> users = userRepository.findAll();
        User foundedUser = new User();
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                foundedUser = user;
                System.out.println("user loaded successfully");
            }
        }
        if (foundedUser.getEmail()==null){
            System.out.println("user not found");
            throw new ResourceNotFoundException("There is no such user email Http status cod 404 not found");

        }


        return foundedUser;

    }

}
