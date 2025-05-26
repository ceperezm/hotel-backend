package com.hotel.backend.controller;

import com.hotel.backend.dto.FacturaDTO;
import com.hotel.backend.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService facturaService;

    @PostMapping
    public ResponseEntity<FacturaDTO> generarFactura(@Valid @RequestBody FacturaDTO dto) {
        FacturaDTO factura = facturaService.generarFactura(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(factura);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacturaDTO> actualizarFactura(@PathVariable Long id, @Valid @RequestBody FacturaDTO dto) {
        FacturaDTO actualizada = facturaService.actualizarFactura(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaDTO> obtenerFacturaPorId(@PathVariable Long id) {
        FacturaDTO factura = facturaService.obtenerFacturaPorId(id);
        return ResponseEntity.ok(factura);
    }

    @GetMapping
    public ResponseEntity<List<FacturaDTO>> listarFacturas() {
        List<FacturaDTO> facturas = facturaService.listarFacturas();
        return ResponseEntity.ok(facturas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long id) {
        facturaService.eliminarFactuta(id);
        return ResponseEntity.noContent().build();
    }
}
