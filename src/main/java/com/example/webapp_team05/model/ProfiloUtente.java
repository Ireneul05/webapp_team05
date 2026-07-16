package com.example.webapp_team05.model;

import java.sql.Date;

public class ProfiloUtente {

    private final String username;
    private final String nome;
    private final String cognome;
    private final Date dataNascita;
    private final String email;
    private final String ruolo;

    public ProfiloUtente(
            String username,
            String nome,
            String cognome,
            Date dataNascita,
            String email,
            String ruolo) {

        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.email = email;
        this.ruolo = ruolo;
    }

    public String getUsername() {
        return username;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public String getEmail() {
        return email;
    }

    public String getRuolo() {
        return ruolo;
    }
}
