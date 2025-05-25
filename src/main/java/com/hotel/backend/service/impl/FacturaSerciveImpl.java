package com.hotel.backend.service.impl;

import com.hotel.backend.dto.FacturaDTO;
import com.hotel.backend.service.FacturaService;

import java.util.List;

public class FacturaSerciveImpl implements FacturaService {
    @Override
    public FacturaDTO generarFactura(FacturaDTO facturaDTO) {
        return null;
    }

    @Override
    public FacturaDTO actualizarFactura(Long id, FacturaDTO facturaDTO) {
        return null;
    }

    @Override
    public FacturaDTO obtenerFacturaPorId(Long id) {
        return null;
    }

    @Override
    public List<FacturaDTO> listarFacturas() {
        return List.of();
    }
}
