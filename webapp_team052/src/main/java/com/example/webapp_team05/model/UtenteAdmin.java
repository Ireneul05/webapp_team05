package com.example.webapp_team05.model;

import java.sql.Timestamp;

public class UtenteAdmin {

    private final String username;
    private final String nome;
    private final String cognome;
    private final String ruolo;
    private final boolean enabled;
    private final Timestamp dataRegistrazione;

    public UtenteAdmin(
            String username,
            String nome,
            String cognome,
            String ruolo,
            boolean enabled,
            Timestamp dataRegistrazione) {

        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.ruolo = ruolo;
        this.enabled = enabled;
        this.dataRegistrazione = dataRegistrazione;
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

    public String getRuolo() {
        return ruolo;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Timestamp getDataRegistrazione() {
        return dataRegistrazione;
    }
}