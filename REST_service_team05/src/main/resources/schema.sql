CREATE TABLE esercizi_programmi (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    nome_programma VARCHAR(50) NOT NULL,
                                    nome_esercizio VARCHAR(100) NOT NULL,
                                    numero_serie INT NOT NULL,
                                    numero_ripetizioni INT NOT NULL,
                                    kcal DOUBLE NOT NULL
);