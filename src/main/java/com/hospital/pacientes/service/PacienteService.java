package com.hospital.pacientes.service;

import com.hospital.pacientes.model.Paciente;
import com.hospital.pacientes.model.Recepcionista;
import com.hospital.pacientes.model.SeguroMedico;
import com.hospital.pacientes.repository.PacienteRepository;
import com.hospital.pacientes.repository.RecepcionistaRepository;
import com.hospital.pacientes.repository.SeguroMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private RecepcionistaRepository recepcionistaRepository;

    @Autowired
    private SeguroMedicoRepository seguroMedicoRepository;

    // ── Registrar nuevo paciente
    public Paciente registrarPaciente(Paciente paciente, Integer idRecepcionista, Integer idSeguro) {

        // Validar que el número de identificación no esté duplicado
        if (pacienteRepository.existsByNumeroIdentificacion(paciente.getNumeroIdentificacion())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un paciente con el número de identificación: "
                            + paciente.getNumeroIdentificacion());
        }

        // Asignar recepcionista 
        Recepcionista recepcionista = recepcionistaRepository.findById(idRecepcionista)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Recepcionista no encontrado con id: " + idRecepcionista));
        paciente.setRecepcionista(recepcionista);

        // Asignar seguro médico 
        if (idSeguro != null) {
            SeguroMedico seguro = seguroMedicoRepository.findById(idSeguro)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Seguro médico no encontrado con id: " + idSeguro));
            paciente.setSeguroMedico(seguro);
        }

        return pacienteRepository.save(paciente);
    }

    // ── Listar todos los pacientes 
    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    // ── Buscar paciente por ID 
    public Paciente obtenerPorId(Integer id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Paciente no encontrado con id: " + id));
    }

    // ── Buscar paciente por número de identificación 
    public Paciente obtenerPorIdentificacion(String numeroIdentificacion) {
        return pacienteRepository.findByNumeroIdentificacion(numeroIdentificacion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Paciente no encontrado con identificación: " + numeroIdentificacion));
    }

    // ── Buscar pacientes por nombre o apellido 
    public List<Paciente> buscarPorNombre(String nombre) {
        return pacienteRepository
                .findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(nombre, nombre);
    }

    // ── Actualizar datos del paciente 
    public Paciente actualizarPaciente(Integer id, Paciente datosActualizados) {
        Paciente pacienteExistente = obtenerPorId(id);

        pacienteExistente.setNombre(datosActualizados.getNombre());
        pacienteExistente.setApellido(datosActualizados.getApellido());
        pacienteExistente.setFechaNacimiento(datosActualizados.getFechaNacimiento());
        pacienteExistente.setDireccion(datosActualizados.getDireccion());
        pacienteExistente.setTelefono(datosActualizados.getTelefono());
        pacienteExistente.setEmail(datosActualizados.getEmail());

        return pacienteRepository.save(pacienteExistente);
    }

    // ── Eliminar paciente 
    public void eliminarPaciente(Integer id) {
        if (!pacienteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Paciente no encontrado con id: " + id);
        }
        pacienteRepository.deleteById(id);
    }
}
