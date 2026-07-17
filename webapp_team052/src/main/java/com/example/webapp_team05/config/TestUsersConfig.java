/*per controllare se l'utente esiste
* cifro la password e inserisci utente in users
* e mette il ruolo in authorieties*/

package com.example.webapp_team05.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.sql.Date;
import java.time.LocalDate;

@Configuration
public class TestUsersConfig {

    @Bean
    public CommandLineRunner creaUtentiTest(
            JdbcUserDetailsManager userManager,
            PasswordEncoder passwordEncoder,
            JdbcTemplate jdbcTemplate) {

        return args -> {

            creaUtenteSeManca(
                    userManager,
                    passwordEncoder,
                    jdbcTemplate,
                    "admin#05",
                    "adm_id_05",
                    "ROLE_ADMIN",
                    "Giovanna",
                    "Verdi",
                    LocalDate.of(1990, 5, 15),
                    "admin@fitnesspets.it"
            );

            creaUtenteSeManca(
                    userManager,
                    passwordEncoder,
                    jdbcTemplate,
                    "basic#05",
                    "bsc_id_05",
                    "ROLE_USER_BASIC",
                    "Luca",
                    "Bianchi",
                    LocalDate.of(1995, 8, 20),
                    "basic@fitnesspets.it"
            );

            creaUtenteSeManca(
                    userManager,
                    passwordEncoder,
                    jdbcTemplate,
                    "pro#05",
                    "pro_id_05",
                    "ROLE_USER_PRO",
                    "Sara",
                    "Rossi",
                    LocalDate.of(1992, 3, 10),
                    "pro@fitnesspets.it"
            );

            creaUtenteSeManca(
                    userManager,
                    passwordEncoder,
                    jdbcTemplate,
                    "prova#1#05",
                    "prv_id_05",
                    "ROLE_USER_PROVA",
                    "Marco",
                    "Neri",
                    LocalDate.of(2000, 11, 6),
                    "prova@fitnesspets.it"
            );
        };
    }

    private void creaUtenteSeManca(
            JdbcUserDetailsManager userManager,
            PasswordEncoder passwordEncoder,
            JdbcTemplate jdbcTemplate,
            String username,
            String password,
            String ruolo,
            String nome,
            String cognome,
            LocalDate dataNascita,
            String email) {

        // Inserimento nelle tabelle gestite da Spring Security
        if (!userManager.userExists(username)) {

            userManager.createUser(
                    User.withUsername(username)
                            .password(passwordEncoder.encode(password))
                            .authorities(ruolo)
                            .disabled(false)
                            .build()
            );

            System.out.println("Creato utente Security: " + username);
        }

        // Inserimento dei dati personali nella tabella utenti
        Integer numeroUtenti = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE username = ?",
                Integer.class,
                username
        );

        if (numeroUtenti != null && numeroUtenti == 0) {

            jdbcTemplate.update(
                    """
                    INSERT INTO users(
                        username,
                        nome,
                        cognome,
                        data_nascita,
                        email,
                        allenamenti_completati
                    )
                    VALUES (?, ?, ?, ?, ?, ?)
                    """,
                    username,
                    nome,
                    cognome,
                    Date.valueOf(dataNascita),
                    email,
                    0
            );

            System.out.println("Creati dati personali: " + username);
        }
    }
}