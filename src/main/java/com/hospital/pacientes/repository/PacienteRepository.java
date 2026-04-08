package com.hospital.pacientes.repository;

import com.hospital.pacientes.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    // Buscar por número de identificación (DPI/CUI)
    Optional<Paciente> findByNumeroIdentificacion(String numeroIdentificacion);

    // Buscar por nombre o apellido (búsqueda parcial)
    List<Paciente> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(
            String nombre, String apellido);

    // Verificar si ya existe un paciente con ese número de identificación
    boolean existsByNumeroIdentificacion(String numeroIdentificacion);

    // Buscar todos los pacientes atendidos por un recepcionista
    List<Paciente> findByRecepcionistaIdRecepcionista(Integer idRecepcionista);
}
