package com.hotel.backend.service.impl;

import com.hotel.backend.dto.PagoDTO;
import com.hotel.backend.service.PagoService;

import java.util.List;

public class PagoServiceImpl implements PagoService {
    @Override
    public PagoDTO registrarPago(PagoDTO pagoDTO) {
        return null;
    }

    @Override
    public List<PagoDTO> listarPagosPorFactura(Long facturaId) {
        return List.of();
    }

    @Override
    public List<PagoDTO> listarTodosPagos() {
        return List.of();
    }
}
