package com.hotel.backend.service;

import com.hotel.backend.dto.FacturaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FacturaService {
    FacturaDTO generarFactura(FacturaDTO facturaDTO);
    FacturaDTO actualizarFactura(Long id, FacturaDTO facturaDTO);
    FacturaDTO obtenerFacturaPorId(Long id);
    List<FacturaDTO> listarFacturas();
}
