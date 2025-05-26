package com.hotel.backend.controller;

import com.hotel.backend.dto.ReservaDTO;
import com.hotel.backend.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    // Crear reserva
    @PostMapping
    public ResponseEntity<ReservaDTO> crearReserva(@Valid @RequestBody ReservaDTO dto) {
        ReservaDTO creada = reservaService.crearReserva(dto);
        return ResponseEntity.status(201).body(creada);
    }

    // Actualizar reserva
    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> actualizarReserva(@PathVariable Long id, @Valid @RequestBody ReservaDTO dto) {
        ReservaDTO actualizada = reservaService.actualizarReserva(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    // Cancelar reserva (cambia su estado)
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarReserva(@PathVariable Long id) {
        reservaService.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }

    // Eliminar reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> obtenerReservaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.obtenerReservaPorId(id));
    }

    // Listar todas las reservas
    @GetMapping
    public ResponseEntity<List<ReservaDTO>> listarReservas() {
        return ResponseEntity.ok(reservaService.listarReservas());
    }

    // Listar reservas por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReservaDTO>> listarReservasPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(reservaService.listarReservasPorUsuario(usuarioId));
    }

    // Realizar Check-In
    @PutMapping("/{id}/checkin")
    public ResponseEntity<Void> realizarCheckIn(@PathVariable Long id) {
        reservaService.realizarCheckIn(id);
        return ResponseEntity.noContent().build();
    }

    // Realizar Check-Out
    @PutMapping("/{id}/checkout")
    public ResponseEntity<Void> realizarCheckOut(@PathVariable Long id) {
        reservaService.realizarCheckOut(id);
        return ResponseEntity.noContent().build();
    }
}
