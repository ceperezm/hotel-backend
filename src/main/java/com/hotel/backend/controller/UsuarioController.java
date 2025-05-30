package com.hotel.backend.controller;

import com.hotel.backend.dto.usuario.UsuarioDTORequest;
import com.hotel.backend.dto.usuario.UsuarioDTOResponse;
import com.hotel.backend.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioDTORequest dto) {
        UsuarioDTOResponse response = usuarioService.crearUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDTOResponse> getUsuarioById(@PathVariable Long id) {
        UsuarioDTOResponse response = usuarioService.findUsuarioById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDTOResponse> getUsuarioByNombreUsuario(@RequestParam String nombreUsuario) {
        UsuarioDTOResponse response = usuarioService.findUsuarioByNombreUsuario(nombreUsuario);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioDTOResponse>> listarUsuarios() {
        List<UsuarioDTOResponse> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDTOResponse> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTORequest dto) {
        UsuarioDTOResponse response = usuarioService.actualizarUsuario(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
