package com.hospital.pacientes.controller;

import com.hospital.pacientes.model.Paciente;
import com.hospital.pacientes.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*") // Para que Angular pueda conectarse
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    // POST /api/pacientes?idRecepcionista=1&idSeguro=2
    // Registrar nuevo paciente 
    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(
            @Valid @RequestBody Paciente paciente,
            @RequestParam Integer idRecepcionista,
            @RequestParam(required = false) Integer idSeguro) {

        Paciente nuevo = pacienteService.registrarPaciente(paciente, idRecepcionista, idSeguro);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // GET /api/pacientes
    // Listar todos los pacientes
    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes() {
        return ResponseEntity.ok(pacienteService.listarPacientes());
    }

    // GET /api/pacientes/{id}
    // Obtener paciente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtenerPaciente(@PathVariable Integer id) {
        return ResponseEntity.ok(pacienteService.obtenerPorId(id));
    }

    // GET /api/pacientes/identificacion/{numero}
    // Buscar paciente por DPI/CUI
    @GetMapping("/identificacion/{numero}")
    public ResponseEntity<Paciente> obtenerPorIdentificacion(@PathVariable String numero) {
        return ResponseEntity.ok(pacienteService.obtenerPorIdentificacion(numero));
    }

    // GET /api/pacientes/buscar?nombre
    // Buscar pacientes por nombre o apellido
    @GetMapping("/buscar")
    public ResponseEntity<List<Paciente>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(pacienteService.buscarPorNombre(nombre));
    }

    // PUT /api/pacientes/{id}
    // Actualizar datos del paciente
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizarPaciente(
            @PathVariable Integer id,
            @Valid @RequestBody Paciente paciente) {

        return ResponseEntity.ok(pacienteService.actualizarPaciente(id, paciente));
    }

    // DELETE /api/pacientes/{id}
    // Eliminar paciente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable Integer id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
