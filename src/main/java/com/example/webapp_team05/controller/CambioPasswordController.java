package com.example.webapp_team05.controller;

import com.example.webapp_team05.service.UtenteService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CambioPasswordController {

    private final UtenteService utenteService;

    public CambioPasswordController(
            UtenteService utenteService) {

        this.utenteService = utenteService;
    }

    @GetMapping({
            "/prova/cambio-password",
            "/basic/cambio-password",
            "/pro/cambio-password"
    })
    public String mostraCambioPassword(
            Authentication authentication,
            HttpServletRequest request,
            Model model) {

        model.addAttribute(
                "username",
                authentication.getName()
        );

        model.addAttribute(
                "dashboardUrl",
                trovaDashboard(request.getRequestURI())
        );

        return "cambio-password";
    }

    @PostMapping({
            "/prova/cambio-password",
            "/basic/cambio-password",
            "/pro/cambio-password"
    })
    public String cambiaPassword(
            Authentication authentication,
            HttpServletRequest request,
            @RequestParam String passwordAttuale,
            @RequestParam String nuovaPassword,
            @RequestParam String confermaPassword,
            RedirectAttributes redirectAttributes) {

        String errore = utenteService.cambiaPassword(
                authentication.getName(),
                passwordAttuale,
                nuovaPassword,
                confermaPassword
        );

        if (errore == null) {

            redirectAttributes.addFlashAttribute(
                    "successo",
                    "Password modificata correttamente."
            );

        } else {

            redirectAttributes.addFlashAttribute(
                    "errore",
                    errore
            );
        }

        return "redirect:" + request.getRequestURI();
    }

    private String trovaDashboard(String percorso) {

        if (percorso.startsWith("/prova/")) {
            return "/prova/dashboard";
        }

        if (percorso.startsWith("/basic/")) {
            return "/basic/dashboard";
        }

        return "/pro/dashboard";
    }
}