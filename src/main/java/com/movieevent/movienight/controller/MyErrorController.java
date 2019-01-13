package com.movieevent.movienight.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@CrossOrigin
@Controller
public class MyErrorController implements ErrorController {

    @GetMapping("/error")

    public String handleError() {



        return "/error";
    }



    @Override

    public String getErrorPath() {

        return "/error";

    }

}
