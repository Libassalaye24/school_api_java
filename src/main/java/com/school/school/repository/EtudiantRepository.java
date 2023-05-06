package com.school.school.repository;

import com.school.school.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EtudiantRepository extends JpaRepository<Etudiant, UUID> {
}
