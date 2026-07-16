package com.example.rest_service_05.model;

public class Esercizio {
    private String nomeEsercizio;
    private int numeroSerie;
    private int numeroRipetizioni;
    private double kcal;

    public Esercizio() {
    }
    public Esercizio(String nomeEsercizio, int numeroSerie, int numeroRipetizioni, double kcal) {
        this.nomeEsercizio = nomeEsercizio;
        this.numeroSerie = numeroSerie;
        this.numeroRipetizioni = numeroRipetizioni;
        this.kcal = kcal;
    }

    public String getNomeEsercizio() {
            return nomeEsercizio;
    }
    public void setNomeEsercizio(String nomeEsercizio) {
        this.nomeEsercizio = nomeEsercizio;
    }

    public int getNumeroSerie() {
        return numeroSerie;
    }
    public void setNumeroSerie(int numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public int getNumeroRipetizioni() {
        return numeroRipetizioni;
    }
    public void setNumeroRipetizioni(int numeroRipetizioni) {
        this.numeroRipetizioni = numeroRipetizioni;
    }

    public double getKcal() {
        return kcal;
    }
    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

}
