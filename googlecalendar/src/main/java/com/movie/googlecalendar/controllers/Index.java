package com.movie.googlecalendar.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Index {
    @RequestMapping(method = RequestMethod.GET ,value = "googleindex")
    public ModelAndView hello() {
        return new ModelAndView("/hello");
    }
    @GetMapping("list")
    public String list(Model model) {

        return "/googleindex";
    }

}
