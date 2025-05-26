package com.hotel.backend.controller;

import com.hotel.backend.dto.HabitacionDTO;
import com.hotel.backend.enums.EstadoHabitacion;
import com.hotel.backend.service.HabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
@RequiredArgsConstructor
public class HabitacionController {

    private final HabitacionService habitacionService;

    // Crear una nueva habitación
    @PostMapping
    public ResponseEntity<HabitacionDTO> registrarHabitacion(@Valid @RequestBody HabitacionDTO dto) {
        HabitacionDTO creada = habitacionService.registrarHabitacion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada); // 201
    }

    // Actualizar habitación por ID
    @PutMapping("/{id}")
    public ResponseEntity<HabitacionDTO> actualizarHabitacion(@PathVariable Long id, @Valid @RequestBody HabitacionDTO dto) {
        HabitacionDTO actualizada = habitacionService.actualizarHabitacion(id, dto);
        return ResponseEntity.ok(actualizada); // 200
    }

    // Cambiar estado de habitación (ocupada, disponible, mantenimiento, etc.)
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstadoHabitacion(@PathVariable Long id, @RequestParam EstadoHabitacion estado) {
        habitacionService.cambiarEstadoHabitacion(id, estado);
        return ResponseEntity.noContent().build(); // 204
    }

    // Obtener habitación por ID
    @GetMapping("/{id}")
    public ResponseEntity<HabitacionDTO> obtenerHabitacion(@PathVariable Long id) {
        HabitacionDTO habitacion = habitacionService.obtenerHabitacionPorId(id);
        return ResponseEntity.ok(habitacion); // 200
    }

    // Listar todas las habitaciones
    @GetMapping
    public ResponseEntity<List<HabitacionDTO>> listarHabitaciones() {
        List<HabitacionDTO> habitaciones = habitacionService.listarHabitaciones();
        return ResponseEntity.ok(habitaciones); // 200
    }

    // Eliminar habitación por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHabitacion(@PathVariable Long id) {
        habitacionService.eliminarHabitacion(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
