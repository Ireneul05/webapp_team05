package com.example.webapp_team05.service;

import com.example.webapp_team05.model.StatisticaAdmin;
import com.example.webapp_team05.model.UtenteAdmin;
import com.example.webapp_team05.repository.UtenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private final UtenteRepository utenteRepository;

    public AdminService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    public List<UtenteAdmin> recuperaTuttiGliUtenti() {
        return utenteRepository.trovaTuttiOrdinati();
    }

    @Transactional
    public int rimuoviUtentiProvaScaduti() {
        return utenteRepository.eliminaUtentiProvaDisabilitati();
    }

    public List<StatisticaAdmin> calcolaStatistiche() {

        List<String> programmi = List.of(
                "Full Body",
                "Push/Pull/Legs",
                "Cardio",
                "Strength"
        );

        int numeroBasic =
                utenteRepository.contaUtentiAttiviPerRuolo(
                        "ROLE_USER_BASIC"
                );

        int numeroPro =
                utenteRepository.contaUtentiAttiviPerRuolo(
                        "ROLE_USER_PRO"
                );

        List<StatisticaAdmin> statistiche = new ArrayList<>();

        for (String programma : programmi) {

            int allenamentiBasic =
                    utenteRepository
                            .contaAllenamentiPerProgrammaERuolo(
                                    programma,
                                    "ROLE_USER_BASIC"
                            );

            int allenamentiPro =
                    utenteRepository
                            .contaAllenamentiPerProgrammaERuolo(
                                    programma,
                                    "ROLE_USER_PRO"
                            );

            double mediaBasic =
                    numeroBasic == 0
                            ? 0.0
                            : (double) allenamentiBasic / numeroBasic;

            double mediaPro =
                    numeroPro == 0
                            ? 0.0
                            : (double) allenamentiPro / numeroPro;

            statistiche.add(
                    new StatisticaAdmin(
                            programma,
                            mediaBasic,
                            mediaPro
                    )
            );
        }

        return statistiche;
    }
}