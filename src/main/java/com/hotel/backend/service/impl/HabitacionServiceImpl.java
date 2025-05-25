package com.hotel.backend.service.impl;

import com.hotel.backend.dto.HabitacionDTO;
import com.hotel.backend.service.HabitacionService;

import java.util.List;

public class HabitacionServiceImpl implements HabitacionService {
    @Override
    public HabitacionDTO registrarHabitacion(HabitacionDTO habitacionDTO) {
        return null;
    }

    @Override
    public HabitacionDTO actualizarHabitacion(Long id, HabitacionDTO habitacionDTO) {
        return null;
    }

    @Override
    public void cambiarEstadoHabitacion(Long id, String nuevoEstado) {

    }

    @Override
    public HabitacionDTO obtenerHabitacionPorId(Long id) {
        return null;
    }

    @Override
    public List<HabitacionDTO> listarHabitaciones() {
        return List.of();
    }
}
