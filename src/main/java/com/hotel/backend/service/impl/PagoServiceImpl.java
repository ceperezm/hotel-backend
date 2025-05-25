package com.hotel.backend.service.impl;

import com.hotel.backend.dto.PagoDTO;
import com.hotel.backend.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
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
