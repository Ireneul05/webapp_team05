package com.example.webapp_team05.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    /*
     * Dopo il login Spring Security manda l'utente
     * all'indirizzo /dashboard.
     */
    @GetMapping("/dashboard")
    public String scegliDashboard(Authentication authentication) {

        if (haRuolo(authentication, "ROLE_ADMIN")) {
            return "redirect:/admin/dashboard";
        }

        if (haRuolo(authentication, "ROLE_USER_PROVA")) {
            return "redirect:/prova/dashboard";
        }

        if (haRuolo(authentication, "ROLE_USER_BASIC")) {
            return "redirect:/basic/dashboard";
        }

        if (haRuolo(authentication, "ROLE_USER_PRO")) {
            return "redirect:/pro/dashboard";
        }

        return "redirect:/login?error=true";
    }

    @GetMapping("/admin/dashboard")
    public String dashboardAdmin(
            Authentication authentication,
            Model model) {

        model.addAttribute("username", authentication.getName());

        return "dashboard-admin";
    }

    @GetMapping("/prova/dashboard")
    public String dashboardProva(
            Authentication authentication,
            Model model) {

        model.addAttribute("username", authentication.getName());

        return "dashboard-prova";
    }

    @GetMapping("/basic/dashboard")
    public String dashboardBasic(
            Authentication authentication,
            Model model) {

        model.addAttribute("username", authentication.getName());

        return "dashboard-basic";
    }

    @GetMapping("/pro/dashboard")
    public String dashboardPro(
            Authentication authentication,
            Model model) {

        model.addAttribute("username", authentication.getName());

        return "dashboard-pro";
    }

    private boolean haRuolo(
            Authentication authentication,
            String ruolo) {

        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals(ruolo));
    }
}