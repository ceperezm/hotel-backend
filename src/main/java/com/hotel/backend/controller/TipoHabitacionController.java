package com.hotel.backend.controller;

import com.hotel.backend.dto.TipoHabitacionDTO;
import com.hotel.backend.service.TipoHabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-habitacion")
@RequiredArgsConstructor
public class TipoHabitacionController {

    private final TipoHabitacionService tipoHabitacionService;

    // Crear un nuevo tipo de habitación
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TipoHabitacionDTO> crearTipo(@Valid @RequestBody TipoHabitacionDTO dto) {
        TipoHabitacionDTO creado = tipoHabitacionService.crearTipo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado); // 201 Created
    }

    // Actualizar un tipo de habitación existente
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TipoHabitacionDTO> actualizarTipo(@PathVariable Long id, @Valid @RequestBody TipoHabitacionDTO dto) {
        TipoHabitacionDTO actualizado = tipoHabitacionService.actualizarTipo(id, dto);
        return ResponseEntity.ok(actualizado); // 200 OK
    }

    // Eliminar un tipo de habitación por ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarTipo(@PathVariable Long id) {
        tipoHabitacionService.eliminarTipo(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // Listar todos los tipos de habitación
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('RECEPCIONISTA')")
    public ResponseEntity<List<TipoHabitacionDTO>> listarTipos() {
        List<TipoHabitacionDTO> lista = tipoHabitacionService.listarTipos();
        return ResponseEntity.ok(lista); // 200 OK
    }
}
