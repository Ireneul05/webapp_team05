package com.example.rest_service_05.controller; // Inserisci il tuo package

import com.example.rest_service_05.model.Esercizio;
import com.example.rest_service_05.repository.ProgrammiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProgrammiController {

    private final ProgrammiRepository programmiRepository;

    // Iniettiamo il repository per poter usare i metodi che parlano col database
    @Autowired
    public ProgrammiController(ProgrammiRepository programmiRepository) {
        this.programmiRepository = programmiRepository;
    }

    // 1. Endpoint per ottenere la lista dei programmi predefiniti (Richiesta 1)
    // Raggiungibile a: GET http://localhost:8081/api/programmi
    @GetMapping("/programmi")
    public List<String> getListaProgrammi() {
        return programmiRepository.getNomiProgrammiPredefiniti();
    }

    // 2. Endpoint per ottenere la composizione di un programma (Richiesta 2)
    // Raggiungibile a: GET http://localhost:8081/api/programmi/dettagli?nome=Cardio
    @GetMapping("/programmi/dettagli")
    public List<Esercizio> getDettagliProgramma(@RequestParam("nome") String nomeProgramma) {
        return programmiRepository.getDettagliProgramma(nomeProgramma);
    }

    // 3. Endpoint per calcolare le kcal di un nuovo programma inserito (Richiesta 3)
    // Raggiungibile a: POST http://localhost:8081/api/programmi/calcola-kcal
    @PostMapping("/programmi/calcola-kcal")
    public double calcolaKcalProgramma(@RequestBody List<Esercizio> nuovoProgramma) {
        double kcalTotali = 0.0;

        // Cicla attraverso tutti gli esercizi ricevuti e somma le kcal
        for (Esercizio esercizio : nuovoProgramma) {
            kcalTotali += esercizio.getKcal();
        }

        return kcalTotali;
    }
}