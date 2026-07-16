package com.example.webapp_team05.repository;

import com.example.webapp_team05.model.UtenteAdmin;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.webapp_team05.model.ProfiloUtente;
import java.util.List;

@Repository
public class UtenteRepository {

    private final JdbcTemplate jdbcTemplate;

    public UtenteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
     * Recupera tutti gli utenti ordinandoli prima per ruolo
     * e poi per data di registrazione.
     */
    public List<UtenteAdmin> trovaTuttiOrdinati() {

        String sql = """
                SELECT
                    u.username,
                    d.nome,
                    d.cognome,
                    a.authority AS ruolo,
                    u.enabled,
                    d.data_registrazione
                FROM users u
                JOIN authorities a
                    ON a.username = u.username
                LEFT JOIN utenti d
                    ON d.username = u.username
                ORDER BY
                    CASE a.authority
                        WHEN 'ROLE_ADMIN' THEN 1
                        WHEN 'ROLE_USER_PROVA' THEN 2
                        WHEN 'ROLE_USER_BASIC' THEN 3
                        WHEN 'ROLE_USER_PRO' THEN 4
                        ELSE 5
                    END,
                    d.data_registrazione ASC
                """;

        return jdbcTemplate.query(
                sql,
                (resultSet, numeroRiga) -> new UtenteAdmin(
                        resultSet.getString("username"),
                        resultSet.getString("nome"),
                        resultSet.getString("cognome"),
                        resultSet.getString("ruolo"),
                        resultSet.getBoolean("enabled"),
                        resultSet.getTimestamp("data_registrazione")
                )
        );
    }

    /*
     * Elimina gli utenti con ruolo ROLE_USER_PROVA
     * che risultano disabilitati.
     */
    public int eliminaUtentiProvaDisabilitati() {

        String sql = """
                DELETE FROM users
                WHERE enabled = FALSE
                  AND username IN (
                        SELECT username
                        FROM authorities
                        WHERE authority = 'ROLE_USER_PROVA'
                  )
                """;

        return jdbcTemplate.update(sql);
    }

    /*
     * Conta gli utenti attivi appartenenti a un determinato ruolo.
     */
    public int contaUtentiAttiviPerRuolo(String ruolo) {

        String sql = """
                SELECT COUNT(*)
                FROM users u
                JOIN authorities a
                    ON a.username = u.username
                WHERE a.authority = ?
                  AND u.enabled = TRUE
                """;

        Integer risultato = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                ruolo
        );

        return risultato != null ? risultato : 0;
    }

    /*
     * Conta gli allenamenti completati per uno specifico
     * programma predefinito e per uno specifico ruolo.
     */
    public int contaAllenamentiPerProgrammaERuolo(
            String nomeProgramma,
            String ruolo) {

        String sql = """
                SELECT COUNT(*)
                FROM allenamenti_eseguiti ae
                JOIN authorities a
                    ON a.username = ae.username
                JOIN users u
                    ON u.username = ae.username
                WHERE ae.nome_programma = ?
                  AND ae.tipo_programma = 'PREDEFINITO'
                  AND a.authority = ?
                  AND u.enabled = TRUE
                """;

        Integer risultato = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                nomeProgramma,
                ruolo
        );

        return risultato != null ? risultato : 0;
    }

    public ProfiloUtente trovaProfiloPerUsername(String username) {

        String sql = """
            SELECT
                u.username,
                u.nome,
                u.cognome,
                u.data_nascita,
                u.email,
                a.authority AS ruolo
            FROM utenti u
            JOIN authorities a
                ON a.username = u.username
            WHERE u.username = ?
            """;

        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, numeroRiga) -> new ProfiloUtente(
                        resultSet.getString("username"),
                        resultSet.getString("nome"),
                        resultSet.getString("cognome"),
                        resultSet.getDate("data_nascita"),
                        resultSet.getString("email"),
                        resultSet.getString("ruolo")
                ),
                username
        );
    }

    public String trovaPasswordPerUsername(String username) {

        String sql = """
            SELECT password
            FROM users
            WHERE username = ?
            """;

        return jdbcTemplate.queryForObject(
                sql,
                String.class,
                username
        );
    }

    public int aggiornaPassword(
            String username,
            String nuovaPasswordCodificata) {

        String sql = """
            UPDATE users
            SET password = ?
            WHERE username = ?
            """;

        return jdbcTemplate.update(
                sql,
                nuovaPasswordCodificata,
                username
        );
    }
}