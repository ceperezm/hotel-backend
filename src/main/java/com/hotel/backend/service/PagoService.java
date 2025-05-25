package com.hotel.backend.service;

import com.hotel.backend.dto.PagoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PagoService {
    PagoDTO registrarPago(PagoDTO pagoDTO);
    List<PagoDTO> listarPagosPorFactura(Long facturaId);
    List<PagoDTO> listarTodosPagos();
}
