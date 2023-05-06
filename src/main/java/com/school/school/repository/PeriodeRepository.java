package com.school.school.repository;

import com.school.school.model.Periode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PeriodeRepository extends JpaRepository<Periode , UUID> {
    Optional<Periode> findByNumero(int numero);

}
