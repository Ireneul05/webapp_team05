package com.example.webapp_team05.controller;

import com.example.webapp_team05.model.ProfiloUtente;
import com.example.webapp_team05.service.UtenteService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfiloController {

    private final UtenteService utenteService;

    public ProfiloController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping({
            "/prova/profilo",
            "/basic/profilo",
            "/pro/profilo"
    })
    public String visualizzaProfilo(
            Authentication authentication,
            Model model) {

        ProfiloUtente profilo =
                utenteService.recuperaProfilo(
                        authentication.getName()
                );

        model.addAttribute("profilo", profilo);

        return "profilo";
    }
}