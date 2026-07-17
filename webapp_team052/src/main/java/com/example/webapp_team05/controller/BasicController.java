package com.example.webapp_team05.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("/allenamenti")
    public String visualizzaAllenamenti(
            Authentication authentication,
            Model model) {

        model.addAttribute(
                "username",
                authentication.getName()
        );

        return "allenamento";
    }

    @GetMapping("/statistiche")
    public String visualizzaStatistiche(
            Authentication authentication,
            Model model) {

        model.addAttribute(
                "username",
                authentication.getName()
        );

        return "statistiche";
    }

    @GetMapping("/upgrade")
    public String visualizzaUpgrade(
            Authentication authentication,
            Model model) {

        model.addAttribute(
                "username",
                authentication.getName()
        );

        return "upgrade";
    }
}