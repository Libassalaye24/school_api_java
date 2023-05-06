package com.school.school.controller;

import com.school.school.exception.InscriptionException;
import com.school.school.model.Classe;
import com.school.school.model.Inscription;
import com.school.school.repository.ClasseRepository;
import com.school.school.repository.InscriptionRepository;
import com.school.school.service.InscriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inscriptions")
public class InscriptionController {

    private final InscriptionService inscriptionService;
    private final InscriptionRepository inscriptionRepository;
    private final ClasseRepository classeRepository;


    public InscriptionController(InscriptionService inscriptionService, InscriptionRepository inscriptionRepository, ClasseRepository classeRepository) {
        this.inscriptionService = inscriptionService;
        this.inscriptionRepository = inscriptionRepository;
        this.classeRepository = classeRepository;
    }

    @PostMapping
    public Inscription inscription(@RequestBody Inscription inscription){
        try {
            return this.inscriptionService.store(inscription);
        }catch (InscriptionException ex){
            throw ex;
        }
    }

    @GetMapping
    public List<Inscription> getAll(){
        return this.inscriptionService.findAll();
    }

//    @GetMapping(name = "/classe/{classeId}")
//    public List<Inscription> getByClasse(@PathVariable UUID classeId){
//        Classe classe1 = classeRepository.findById(classeId).orElse(null);
//        if (classe1 != null){
//            return  this.inscriptionRepository.findAllByClasse(classe1);
//        }
//        throw  new RuntimeException("Classe with id :"+classeId+" not found");
//    }
}
