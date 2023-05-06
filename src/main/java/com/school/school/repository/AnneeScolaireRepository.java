package com.school.school.repository;

import com.school.school.model.Annee;
import com.school.school.model.Etudiant;
import com.school.school.model.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AnneeScolaireRepository extends JpaRepository<Annee, UUID> {
    Optional<Annee> getAnneeByEtat(boolean etat);
    Optional<Annee> findAnneeByLibelle(String libelle);



}
