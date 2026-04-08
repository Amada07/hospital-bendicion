package com.hospital.pacientes.service;

import com.hospital.pacientes.model.SeguroMedico;
import com.hospital.pacientes.repository.SeguroMedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SeguroMedicoService {

    @Autowired
    private SeguroMedicoRepository seguroMedicoRepository;

    public List<SeguroMedico> listar() {
        return seguroMedicoRepository.findAll();
    }

    public SeguroMedico obtenerPorId(Integer id) {
        return seguroMedicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Seguro médico no encontrado con id: " + id));
    }

    public SeguroMedico crear(SeguroMedico seguro) {
        if (seguroMedicoRepository.existsByNumeroPoliza(seguro.getNumeroPoliza())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un seguro con la póliza: " + seguro.getNumeroPoliza());
        }
        return seguroMedicoRepository.save(seguro);
    }

    public SeguroMedico actualizar(Integer id, SeguroMedico datos) {
        SeguroMedico existente = obtenerPorId(id);
        existente.setNombreAseguradora(datos.getNombreAseguradora());
        existente.setNumeroPoliza(datos.getNumeroPoliza());
        existente.setFechaVencimiento(datos.getFechaVencimiento());
        return seguroMedicoRepository.save(existente);
    }

    public void eliminar(Integer id) {
        if (!seguroMedicoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Seguro médico no encontrado con id: " + id);
        }
        seguroMedicoRepository.deleteById(id);
    }
}
