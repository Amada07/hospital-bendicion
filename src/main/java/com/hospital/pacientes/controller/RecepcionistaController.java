package com.hospital.pacientes.controller;

import com.hospital.pacientes.model.Recepcionista;
import com.hospital.pacientes.service.RecepcionistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recepcionistas")
@CrossOrigin(origins = "*")
public class RecepcionistaController {

    @Autowired
    private RecepcionistaService recepcionistaService;

    // GET /api/recepcionistas
    @GetMapping
    public ResponseEntity<List<Recepcionista>> listar() {
        return ResponseEntity.ok(recepcionistaService.listar());
    }

    // GET /api/recepcionistas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Recepcionista> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(recepcionistaService.obtenerPorId(id));
    }

    // POST /api/recepcionistas
    @PostMapping
    public ResponseEntity<Recepcionista> crear(@RequestBody Recepcionista recepcionista) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recepcionistaService.crear(recepcionista));
    }

    // PUT /api/recepcionistas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Recepcionista> actualizar(
            @PathVariable Integer id, @RequestBody Recepcionista datos) {
        return ResponseEntity.ok(recepcionistaService.actualizar(id, datos));
    }

    // DELETE /api/recepcionistas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        recepcionistaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
