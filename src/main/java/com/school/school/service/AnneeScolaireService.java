package com.school.school.service;

import com.school.school.model.Annee;
import com.school.school.repository.AnneeScolaireRepository;
import org.springframework.stereotype.Service;

@Service
public class AnneeScolaireService {

    private final AnneeScolaireRepository anneeScolaireRepository;

    public AnneeScolaireService(AnneeScolaireRepository anneeScolaireRepository) {
        this.anneeScolaireRepository = anneeScolaireRepository;
    }

    public Annee store(Annee annee){
        String libelle = annee.getDebut()+"-"+annee.getFin();
        annee.setLibelle(libelle);
        if (!annee.isEtat()){
            annee.setEtat(false);
        }
        return this.anneeScolaireRepository.save(annee);
    }
}
