package com.hotel.backend.service;

import com.hotel.backend.dto.PagoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PagoService {
    PagoDTO registrarPago(PagoDTO pagoDTO);
    PagoDTO actualizarPago(Long id, PagoDTO dto);
    void eliminarPago(Long id);

    PagoDTO obtenerPagoPorId(Long id);

    List<PagoDTO> listarPagosPorFactura(Long facturaId);
    List<PagoDTO> listarTodosPagos();
}
