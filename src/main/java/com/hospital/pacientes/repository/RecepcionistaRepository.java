package com.hospital.pacientes.repository;

import com.hospital.pacientes.model.Recepcionista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecepcionistaRepository extends JpaRepository<Recepcionista, Integer> {

    // Buscar recepcionista por email
    java.util.Optional<Recepcionista> findByEmail(String email);
}
