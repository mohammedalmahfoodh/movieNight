package com.movieevent.movienight.controller;

import com.movieevent.movienight.Repository.UserRepository;
import com.movieevent.movienight.entity.User;
import com.movieevent.movienight.service.UserService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/user/saveuser")
    public void saveUser(@RequestBody User user) {

         userService.saveUser(user);
    }


    @GetMapping("/user/{email}")
    public User getUserByEmail(@PathVariable String email) {

        return userService.getUserByEmail(email);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/checkstarteventtime/{timeToCheck}",produces = "application/json")
    public Map<String, String> cheackValidity(@PathVariable String timeToCheck) {
       // System.out.println(user.getEmail());
    return    userService.cheackStartBookingValidity(timeToCheck);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/checkendeventtime/{timeToCheck}",produces = "application/json")
    public Map<String, String> cheackEndEventValidity(@PathVariable String timeToCheck) {

        return    userService.cheackEndtBookingValidity(timeToCheck);

    }
    @RequestMapping(method = RequestMethod.GET, value = "/user/createmovieevent")
    public ResponseEntity<String> createMovieEvent() {

      return   userService.createMovieEvent();
    }


    @RequestMapping(method = RequestMethod.GET, value="/user/testgetuser")
    public ResponseEntity<String> getUser(@RequestBody String time){
        System.out.println(time + "  By post test");
        return ResponseEntity.status(HttpStatus.OK)
                .body("valid" );
    }

    @RequestMapping(method = RequestMethod.GET, value="/user/deletemovieevent")
public Map<String, String>deleteGoogleEvent(){
       return userService.deleteGoogleEvent();
}


}
