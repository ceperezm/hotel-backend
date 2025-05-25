package com.hotel.backend.service;

import com.hotel.backend.dto.HabitacionDTO;
import com.hotel.backend.enums.EstadoHabitacion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HabitacionService {
    HabitacionDTO registrarHabitacion(HabitacionDTO habitacionDTO);
    HabitacionDTO actualizarHabitacion(Long id, HabitacionDTO habitacionDTO);
    void cambiarEstadoHabitacion(Long id, EstadoHabitacion nuevoEstado);
    HabitacionDTO obtenerHabitacionPorId(Long id);
    List<HabitacionDTO> listarHabitaciones();
    void eliminarHabitacion(Long id);
}
