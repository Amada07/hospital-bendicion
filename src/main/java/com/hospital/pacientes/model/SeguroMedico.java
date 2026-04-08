package com.hospital.pacientes.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "seguro_medico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeguroMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguro")
    private Integer idSeguro;

    @Column(name = "nombre_aseguradora", nullable = false, length = 150)
    private String nombreAseguradora;

    @Column(name = "numero_poliza", nullable = false, length = 80)
    private String numeroPoliza;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
}
