package com.school.school.repository;

import com.school.school.model.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FiliereRepository extends JpaRepository<Filiere, UUID> {
}
