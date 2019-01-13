package com.movieevent.movienight.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
@CrossOrigin
@Controller
public class IndexLogIn {
    @RequestMapping(method = RequestMethod.GET ,value = "googlelogin")
    public ModelAndView hello() {
        return new ModelAndView("googlelink");
    }



}
