package com.hotel.backend.service;

import com.hotel.backend.dto.HuespedDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HuespedService {
    HuespedDTO registrarHuesped(HuespedDTO huespedDTO);
    HuespedDTO actualizarHuesped(Long id, HuespedDTO huespedDTO);
    void eliminarHuesped(Long id);
    HuespedDTO obtenerHuespedPorId(Long id);
    List<HuespedDTO> listarHuespedes();
}
