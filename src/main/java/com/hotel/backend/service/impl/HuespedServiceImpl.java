package com.hotel.backend.service.impl;

import com.hotel.backend.dto.HuespedDTO;
import com.hotel.backend.service.HuespedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HuespedServiceImpl implements HuespedService {
    @Override
    public HuespedDTO registrarHuesped(HuespedDTO huespedDTO) {
        return null;
    }

    @Override
    public HuespedDTO actualizarHuesped(Long id, HuespedDTO huespedDTO) {
        return null;
    }

    @Override
    public void eliminarHuesped(Long id) {

    }

    @Override
    public HuespedDTO obtenerHuespedPorId(Long id) {
        return null;
    }

    @Override
    public List<HuespedDTO> listarHuespedes() {
        return List.of();
    }
}
