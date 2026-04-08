package com.hospital.pacientes;

import com.hospital.pacientes.model.Paciente;
import com.hospital.pacientes.model.Recepcionista;
import com.hospital.pacientes.model.SeguroMedico;
import com.hospital.pacientes.repository.PacienteRepository;
import com.hospital.pacientes.repository.RecepcionistaRepository;
import com.hospital.pacientes.repository.SeguroMedicoRepository;
import com.hospital.pacientes.service.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private RecepcionistaRepository recepcionistaRepository;

    @Mock
    private SeguroMedicoRepository seguroMedicoRepository;

    @InjectMocks
    private PacienteService pacienteService;

    private Paciente paciente;
    private Recepcionista recepcionista;

    @BeforeEach
    void setUp() {
        recepcionista = new Recepcionista(1, "María", "López", "55551234", "maria@hospital.com");

        paciente = new Paciente();
        paciente.setIdPaciente(1);
        paciente.setNumeroIdentificacion("1234567890101");
        paciente.setNombre("Juan");
        paciente.setApellido("García");
        paciente.setFechaNacimiento(LocalDate.of(1990, 5, 15));
        paciente.setDireccion("Zona 1, Guatemala");
        paciente.setTelefono("55559999");
        paciente.setRecepcionista(recepcionista);
    }

    // ── REGISTRAR PACIENTE ────────────────────────────────────────────────

    @Test
    void registrarPaciente_exitoso() {
        when(pacienteRepository.existsByNumeroIdentificacion(any())).thenReturn(false);
        when(recepcionistaRepository.findById(1)).thenReturn(Optional.of(recepcionista));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        Paciente resultado = pacienteService.registrarPaciente(paciente, 1, null);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    void registrarPaciente_identificacionDuplicada_lanzaConflict() {
        when(pacienteRepository.existsByNumeroIdentificacion("1234567890101")).thenReturn(true);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> pacienteService.registrarPaciente(paciente, 1, null));

        assertEquals(409, ex.getStatusCode().value()); // 409 CONFLICT
    }

    @Test
    void registrarPaciente_recepcionistaNoExiste_lanzaNotFound() {
        when(pacienteRepository.existsByNumeroIdentificacion(any())).thenReturn(false);
        when(recepcionistaRepository.findById(99)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> pacienteService.registrarPaciente(paciente, 99, null));

        assertEquals(404, ex.getStatusCode().value()); // 404 NOT FOUND
    }

    @Test
    void registrarPaciente_conSeguro_asignaCorrectamente() {
        SeguroMedico seguro = new SeguroMedico(1, "Seguros Guatemala", "POL-001",
                LocalDate.of(2025, 12, 31));

        when(pacienteRepository.existsByNumeroIdentificacion(any())).thenReturn(false);
        when(recepcionistaRepository.findById(1)).thenReturn(Optional.of(recepcionista));
        when(seguroMedicoRepository.findById(1)).thenReturn(Optional.of(seguro));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        pacienteService.registrarPaciente(paciente, 1, 1);

        verify(seguroMedicoRepository, times(1)).findById(1);
    }

    // ── LISTAR PACIENTES ──────────────────────────────────────────────────

    @Test
    void listarPacientes_retornaLista() {
        when(pacienteRepository.findAll()).thenReturn(List.of(paciente));

        List<Paciente> lista = pacienteService.listarPacientes();

        assertEquals(1, lista.size());
        assertEquals("García", lista.get(0).getApellido());
    }

    // ── OBTENER POR ID ────────────────────────────────────────────────────

    @Test
    void obtenerPorId_existente_retornaPaciente() {
        when(pacienteRepository.findById(1)).thenReturn(Optional.of(paciente));

        Paciente resultado = pacienteService.obtenerPorId(1);

        assertEquals(1, resultado.getIdPaciente());
    }

    @Test
    void obtenerPorId_noExiste_lanzaNotFound() {
        when(pacienteRepository.findById(99)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> pacienteService.obtenerPorId(99));

        assertEquals(404, ex.getStatusCode().value());
    }

    // ── ACTUALIZAR ────────────────────────────────────────────────────────

    @Test
    void actualizarPaciente_exitoso() {
        Paciente datosNuevos = new Paciente();
        datosNuevos.setNombre("Pedro");
        datosNuevos.setApellido("Pérez");
        datosNuevos.setFechaNacimiento(LocalDate.of(1985, 3, 10));
        datosNuevos.setDireccion("Zona 5, Guatemala");
        datosNuevos.setTelefono("55558888");

        when(pacienteRepository.findById(1)).thenReturn(Optional.of(paciente));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        Paciente resultado = pacienteService.actualizarPaciente(1, datosNuevos);

        assertNotNull(resultado);
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    // ── ELIMINAR ──────────────────────────────────────────────────────────

    @Test
    void eliminarPaciente_exitoso() {
        when(pacienteRepository.existsById(1)).thenReturn(true);
        doNothing().when(pacienteRepository).deleteById(1);

        assertDoesNotThrow(() -> pacienteService.eliminarPaciente(1));
        verify(pacienteRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarPaciente_noExiste_lanzaNotFound() {
        when(pacienteRepository.existsById(99)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> pacienteService.eliminarPaciente(99));

        assertEquals(404, ex.getStatusCode().value());
    }
}
