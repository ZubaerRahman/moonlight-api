package com.fyp.moonlight.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/home")
public class HomePageController {

    @GetMapping("")
    public String renderHome(Authentication authentication) {
        return "Home";
    }
}
