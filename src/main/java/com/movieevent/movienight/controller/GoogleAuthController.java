package com.movieevent.movienight.controller;

import com.movieevent.movienight.entity.User;
import com.movieevent.movienight.service.SaveGoogleAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GoogleAuthController {

    @Autowired
    SaveGoogleAuthService googleAuthService;

    @RequestMapping(value = "/storegoogleauthcode", method = RequestMethod.POST)

    public String storeauthcode(@RequestBody String code, @RequestHeader("X-Requested-With") String encoding) {

        googleAuthService.storeauthcode(code,encoding);

        return "ok";
    }

    @RequestMapping("/user/authenticatedusers")
    public List<User> getAuthenticated() {

        return googleAuthService.loadUsers();
    }


}
