package com.hospital.pacientes.service;

import com.hospital.pacientes.model.Recepcionista;
import com.hospital.pacientes.repository.RecepcionistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RecepcionistaService {

    @Autowired
    private RecepcionistaRepository recepcionistaRepository;

    public List<Recepcionista> listar() {
        return recepcionistaRepository.findAll();
    }

    public Recepcionista obtenerPorId(Integer id) {
        return recepcionistaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Recepcionista no encontrado con id: " + id));
    }

    public Recepcionista crear(Recepcionista recepcionista) {
        return recepcionistaRepository.save(recepcionista);
    }

    public Recepcionista actualizar(Integer id, Recepcionista datos) {
        Recepcionista existente = obtenerPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setApellido(datos.getApellido());
        existente.setTelefono(datos.getTelefono());
        existente.setEmail(datos.getEmail());
        return recepcionistaRepository.save(existente);
    }

    public void eliminar(Integer id) {
        if (!recepcionistaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Recepcionista no encontrado con id: " + id);
        }
        recepcionistaRepository.deleteById(id);
    }
}
