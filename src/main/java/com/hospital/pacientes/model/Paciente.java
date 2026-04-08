package com.hospital.pacientes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "paciente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    private Integer idPaciente;

    @Column(name = "numero_identificacion", unique = true, nullable = false, length = 20)
    @NotBlank(message = "El número de identificación es obligatorio")
    private String numeroIdentificacion;

    @Column(name = "nombre", nullable = false, length = 100)
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Column(name = "fecha_nacimiento", nullable = false)
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @Column(name = "direccion", nullable = false, length = 255)
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @Column(name = "telefono", nullable = false, length = 20)
    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @Column(name = "email", length = 100)
    @Email(message = "El email no tiene un formato válido")
    private String email;

    // Relación con seguro médico (opcional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_seguro")
    private SeguroMedico seguroMedico;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    // Relación con recepcionista que registra al paciente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recepcionista", nullable = false)
    @NotNull(message = "El recepcionista es obligatorio")
    private Recepcionista recepcionista;

    // Se asigna automáticamente antes de guardar
    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
    }
}
