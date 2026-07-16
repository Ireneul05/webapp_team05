package com.example.webapp_team05.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    /*@GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }*/


    @GetMapping("/signup-success")
    public String signupSuccess() {
        return "signup-success";
    }
    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }
    @GetMapping("/contatti")
    public String contatti() {
        return "contatti";
    }
}