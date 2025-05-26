package com.hotel.backend.controller;

import com.hotel.backend.dto.PagoDTO;
import com.hotel.backend.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    // Registrar nuevo pago
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<PagoDTO> registrarPago(@Valid @RequestBody PagoDTO dto) {
        PagoDTO creado = pagoService.registrarPago(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado); // 201
    }

    // Actualizar pago existente
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<PagoDTO> actualizarPago(@PathVariable Long id, @Valid @RequestBody PagoDTO dto) {
        PagoDTO actualizado = pagoService.actualizarPago(id, dto);
        return ResponseEntity.ok(actualizado); // 200
    }

    // Eliminar un pago
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<Void> eliminarPago(@PathVariable Long id) {
        pagoService.eliminarPago(id);
        return ResponseEntity.noContent().build(); // 204
    }

    // Obtener un pago por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<PagoDTO> obtenerPago(@PathVariable Long id) {
        PagoDTO pago = pagoService.obtenerPagoPorId(id);
        return ResponseEntity.ok(pago); // 200
    }

    // Listar todos los pagos
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<List<PagoDTO>> listarPagos() {
        List<PagoDTO> lista = pagoService.listarTodosPagos();
        return ResponseEntity.ok(lista); // 200
    }

    // Listar pagos por ID de factura
    @GetMapping("/factura/{facturaId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
    public ResponseEntity<List<PagoDTO>> listarPagosPorFactura(@PathVariable Long facturaId) {
        List<PagoDTO> lista = pagoService.listarPagosPorFactura(facturaId);
        return ResponseEntity.ok(lista); // 200
    }
}
