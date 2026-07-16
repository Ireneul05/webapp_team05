/* Tabelle necessarie per login, ruoli e gestione utenti */


/* Credenziali utilizzate da Spring Security */

CREATE TABLE IF NOT EXISTS users (
                                     username VARCHAR(100) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL
    );


/* Ruoli utilizzati da Spring Security */

CREATE TABLE IF NOT EXISTS authorities (
                                           username VARCHAR(100) NOT NULL,
    authority VARCHAR(50) NOT NULL,

    CONSTRAINT fk_authorities_users
    FOREIGN KEY (username)
    REFERENCES users(username)
    ON DELETE CASCADE,

    CONSTRAINT uk_username_authority
    UNIQUE (username, authority)
    );


/* Dati personali degli utenti */

CREATE TABLE IF NOT EXISTS utenti (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      username VARCHAR(100) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    cognome VARCHAR(100) NOT NULL,
    data_nascita DATE NOT NULL,
    email VARCHAR(150) NOT NULL,
    data_registrazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    allenamenti_completati INT DEFAULT 0,

    CONSTRAINT fk_utenti_users
    FOREIGN KEY (username)
    REFERENCES users(username)
    ON DELETE CASCADE
    );


/* Recensioni della web app */

CREATE TABLE IF NOT EXISTS recensioni (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          username VARCHAR(100) NOT NULL,
    testo VARCHAR(500) NOT NULL,
    data_recensione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_recensioni_users
    FOREIGN KEY (username)
    REFERENCES users(username)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS allenamenti_eseguiti (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,

     username VARCHAR(100) NOT NULL,
    nome_programma VARCHAR(100) NOT NULL,
    tipo_programma VARCHAR(30) NOT NULL DEFAULT 'PREDEFINITO',

    data_completamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_allenamenti_users
    FOREIGN KEY (username)
    REFERENCES users(username)
    ON DELETE CASCADE
    );