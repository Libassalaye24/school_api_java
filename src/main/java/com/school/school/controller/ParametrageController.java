package com.school.school.controller;


import com.school.school.model.Annee;
import com.school.school.model.Classe;
import com.school.school.model.Filiere;
import com.school.school.model.Periode;
import com.school.school.repository.AnneeScolaireRepository;
import com.school.school.repository.ClasseRepository;
import com.school.school.repository.FiliereRepository;
import com.school.school.repository.PeriodeRepository;
import com.school.school.service.AnneeScolaireService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/settings")
public class ParametrageController {

    private final FiliereRepository filiereRepository;
    private final ClasseRepository classeRepository;
    private final AnneeScolaireRepository anneeScolaireRepository;
    private final AnneeScolaireService anneeScolaireService;
    private final PeriodeRepository periodeRepository;

    public ParametrageController(FiliereRepository filiereRepository, ClasseRepository classeRepository, AnneeScolaireRepository anneeScolaireRepository, AnneeScolaireService anneeScolaireService, PeriodeRepository periodeRepository) {
        this.filiereRepository = filiereRepository;
        this.classeRepository = classeRepository;
        this.anneeScolaireRepository = anneeScolaireRepository;
        this.anneeScolaireService = anneeScolaireService;
        this.periodeRepository = periodeRepository;
    }

    @PostMapping("/filiere")
    public Filiere storeFiliere(@RequestBody Filiere filiere){
        return this.filiereRepository.save(filiere);
    }

    @GetMapping("/filiere")
    public List<Filiere> findFilieres(){
        return this.filiereRepository.findAll();
    }

    @PostMapping("/classe")
    public Classe storeClasse(@RequestBody Classe classe){
        return this.classeRepository.save(classe);
    }

    @GetMapping("/classe")
    public List<Classe> findClasses(){
        return this.classeRepository.findAll();
    }

    @PostMapping("/annee")
    public Annee storeAnnee(@RequestBody Annee annee){
        return this.anneeScolaireService.store(annee);
    }

    @GetMapping("/annee")
    public List<Annee> findAnnees(){
        return this.anneeScolaireRepository.findAll();
    }

    @PostMapping("/periode")
    public Periode storePeriode(@RequestBody Periode periode){
        return this.periodeRepository.save(periode);
    }

    @GetMapping("/periode")
    public List<Periode> findPeriodes(){
        return this.periodeRepository.findAll();
    }
}
