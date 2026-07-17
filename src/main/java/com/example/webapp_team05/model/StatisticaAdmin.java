package com.example.webapp_team05.model;

public class StatisticaAdmin {

    private final String nomeProgramma;
    private final double mediaBasic;
    private final double mediaPro;

    public StatisticaAdmin(
            String nomeProgramma,
            double mediaBasic,
            double mediaPro) {

        this.nomeProgramma = nomeProgramma;
        this.mediaBasic = mediaBasic;
        this.mediaPro = mediaPro;
    }

    public String getNomeProgramma() {
        return nomeProgramma;
    }

    public double getMediaBasic() {
        return mediaBasic;
    }

    public double getMediaPro() {
        return mediaPro;
    }
}