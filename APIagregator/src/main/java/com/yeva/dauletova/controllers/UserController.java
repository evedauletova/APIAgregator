package com.yeva.dauletova.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")

public class UserController {
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
    @GetMapping("/profile")
    public String profile(){
        return "userprofile";
    }
}
