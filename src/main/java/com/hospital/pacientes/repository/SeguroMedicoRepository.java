package com.hospital.pacientes.repository;

import com.hospital.pacientes.model.SeguroMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeguroMedicoRepository extends JpaRepository<SeguroMedico, Integer> {

    // Buscar seguro por número de póliza
    Optional<SeguroMedico> findByNumeroPoliza(String numeroPoliza);

    // Verificar si existe una póliza
    boolean existsByNumeroPoliza(String numeroPoliza);
}
