package com.school.school.repository;

import com.school.school.model.Annee;
import com.school.school.model.Classe;
import com.school.school.model.Etudiant;
import com.school.school.model.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InscriptionRepository extends JpaRepository<Inscription, UUID> {

    List<Inscription> findAllByClasse(Classe classe);
    Inscription findByEtudiantAndAnneescolaire(Etudiant etudiant , Annee anneeScolaire);
    List<Inscription> findByAnneescolaire(Annee anneescolaire);

    @Query("SELECT i FROM Inscription i WHERE i.etudiant = :etudiant ORDER BY i.date DESC")
    Inscription findLatestInscriptionByEtudiant(@Param("etudiant") Etudiant etudiant);
}
