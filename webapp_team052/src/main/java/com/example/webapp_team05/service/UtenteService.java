package com.example.webapp_team05.service;

import com.example.webapp_team05.model.ProfiloUtente;
import com.example.webapp_team05.repository.UtenteRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public UtenteService(
            UtenteRepository utenteRepository,
            PasswordEncoder passwordEncoder) {

        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ProfiloUtente recuperaProfilo(String username) {
        return utenteRepository.trovaProfiloPerUsername(username);
    }

    @Transactional
    public String cambiaPassword(
            String username,
            String passwordAttuale,
            String nuovaPassword,
            String confermaPassword) {

        if (passwordAttuale == null ||
                nuovaPassword == null ||
                confermaPassword == null) {

            return "Tutti i campi sono obbligatori.";
        }

        String passwordSalvata =
                utenteRepository.trovaPasswordPerUsername(username);

        if (!passwordEncoder.matches(
                passwordAttuale,
                passwordSalvata)) {

            return "La password attuale non è corretta.";
        }

        if (!nuovaPassword.equals(confermaPassword)) {

            return "La nuova password e la conferma non coincidono.";
        }

        if (nuovaPassword.length() != 8) {

            return "La nuova password deve essere lunga esattamente 8 caratteri.";
        }

        if (!nuovaPassword.contains("id_05")) {

            return "La nuova password deve contenere la stringa id_05.";
        }

        if (passwordEncoder.matches(
                nuovaPassword,
                passwordSalvata)) {

            return "La nuova password deve essere diversa da quella attuale.";
        }

        String nuovaPasswordCodificata =
                passwordEncoder.encode(nuovaPassword);

        int righeModificate =
                utenteRepository.aggiornaPassword(
                        username,
                        nuovaPasswordCodificata
                );

        if (righeModificate != 1) {
            return "Non è stato possibile modificare la password.";
        }

        return null;
    }
}

/*se null vuol dire che cambio password riuscito*/