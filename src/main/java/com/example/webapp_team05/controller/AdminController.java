package com.example.webapp_team05.controller;

import com.example.webapp_team05.model.StatisticaAdmin;
import com.example.webapp_team05.service.AdminService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/utenti")
    public String visualizzaUtenti(
            Authentication authentication,
            Model model) {

        model.addAttribute(
                "username",
                authentication.getName()
        );

        model.addAttribute(
                "utenti",
                adminService.recuperaTuttiGliUtenti()
        );

        return "lista-utenti";
    }

    @PostMapping("/rimuovi-scaduti")
    public String rimuoviUtentiScaduti(
            RedirectAttributes redirectAttributes) {

        int utentiRimossi =
                adminService.rimuoviUtentiProvaScaduti();

        redirectAttributes.addFlashAttribute(
                "utentiRimossi",
                utentiRimossi
        );

        if (utentiRimossi == 0) {
            redirectAttributes.addFlashAttribute(
                    "successo",
                    "Non sono presenti utenti Prova scaduti."
            );
        } else {
            redirectAttributes.addFlashAttribute(
                    "successo",
                    "Rimozione completata correttamente."
            );
        }

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/statistiche")
    public String visualizzaStatistiche(
            Authentication authentication,
            Model model) {

        List<StatisticaAdmin> statistiche =
                adminService.calcolaStatistiche();

        model.addAttribute(
                "username",
                authentication.getName()
        );

        model.addAttribute(
                "statistiche",
                statistiche
        );

        model.addAttribute(
                "etichette",
                statistiche.stream()
                        .map(StatisticaAdmin::getNomeProgramma)
                        .toList()
        );

        model.addAttribute(
                "medieBasic",
                statistiche.stream()
                        .map(StatisticaAdmin::getMediaBasic)
                        .toList()
        );

        model.addAttribute(
                "mediePro",
                statistiche.stream()
                        .map(StatisticaAdmin::getMediaPro)
                        .toList()
        );

        return "statistiche-admin";
    }
}