package com.example.rest_service_05.repository;

import com.example.rest_service_05.model.Esercizio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProgrammiRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProgrammiRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getNomiProgrammiPredefiniti() {
        String sql = "SELECT DISTINCT nome_programma FROM esercizi_programmi";

        return jdbcTemplate.queryForList(sql, String.class);
    }

    public List<Esercizio> getDettagliProgramma(String nomeProgramma) {
        String sql = "SELECT nome_esercizio, numero_serie, numero_ripetizioni, kcal FROM esercizi_programmi WHERE nome_programma = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Esercizio esercizio = new Esercizio();

            esercizio.setNomeEsercizio(rs.getString("nome_esercizio"));
            esercizio.setNumeroSerie(rs.getInt("numero_serie"));
            esercizio.setNumeroRipetizioni(rs.getInt("numero_ripetizioni"));
            esercizio.setKcal(rs.getDouble("kcal"));
            return esercizio;
        }, nomeProgramma);
    }
}
