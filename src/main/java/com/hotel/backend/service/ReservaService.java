package com.hotel.backend.service;

import com.hotel.backend.dto.ReservaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservaService {
    ReservaDTO crearReserva(ReservaDTO reservaDTO);
    ReservaDTO actualizarReserva(Long id, ReservaDTO reservaDTO);
    void cancelarReserva(Long id);
    ReservaDTO obtenerReservaPorId(Long id);
    List<ReservaDTO> listarReservas();
    List<ReservaDTO> listarReservasPorUsuario(Long usuarioId);
    void realizarCheckIn(Long reservaId);
    void realizarCheckOut(Long reservaId);
}
