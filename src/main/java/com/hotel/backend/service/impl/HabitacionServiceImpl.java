package com.hotel.backend.service.impl;

import com.hotel.backend.dto.HabitacionDTO;
import com.hotel.backend.dto.mapper.HabitacionMapper;
import com.hotel.backend.enums.EstadoHabitacion;
import com.hotel.backend.exception.ResourceNotFoundException;
import com.hotel.backend.model.Habitacion;
import com.hotel.backend.model.TipoHabitacion;
import com.hotel.backend.repository.HabitacionRepository;
import com.hotel.backend.repository.TipoHabitacionRepository;
import com.hotel.backend.service.HabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {
    private final HabitacionRepository habitacionRepository;
    private final TipoHabitacionRepository tipoHabitacionRepository;
    private final HabitacionMapper habitacionMapper;

    @Override
    public HabitacionDTO registrarHabitacion(HabitacionDTO dto) {
        TipoHabitacion tipoHabitacion = tipoHabitacionRepository.findById(dto.getTipoHabitacionId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de habitacion no encontrado"));

        Habitacion habitacion = new Habitacion();
        habitacion.setNumero(dto.getNumero());
        habitacion.setEstado(dto.getEstado());
        habitacion.setDescripcion(dto.getDescripcion());
        habitacion.setTipoHabitacion(tipoHabitacion);
        return habitacionMapper.toDTO(habitacionRepository.save(habitacion));
    }

    @Override
    public HabitacionDTO actualizarHabitacion(Long id, HabitacionDTO dto) {
        Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitacion no encontrada con id: " + id));

        TipoHabitacion tipoHabitacion = tipoHabitacionRepository.findById(dto.getTipoHabitacionId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de habitacion no encontrado"));

        habitacion.setNumero(dto.getNumero());
        habitacion.setEstado(dto.getEstado());
        habitacion.setDescripcion(dto.getDescripcion());
        habitacion.setTipoHabitacion(tipoHabitacion);

        return  habitacionMapper.toDTO(habitacionRepository.save(habitacion));
    }

    @Override
    public void cambiarEstadoHabitacion(Long id, EstadoHabitacion nuevoEstado) {
        Habitacion habitacion = habitacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habitación no encontrada con ID: " + id));

        habitacion.setEstado(nuevoEstado);
        habitacionRepository.save(habitacion);
    }

    @Override
    public HabitacionDTO obtenerHabitacionPorId(Long id) {
        return habitacionRepository.findById(id)
                .map(habitacionMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Habitacion no encontrada con id: " + id));

    }

    @Override
    public List<HabitacionDTO> listarHabitaciones() {
        return habitacionRepository.findAll().stream()
                .map(habitacionMapper::toDTO)
                .toList();
    }

    @Override
    public void eliminarHabitacion(Long id) {
        if(!habitacionRepository.existsById(id)){
            throw new ResourceNotFoundException("Habitacion no encontrada con id: " + id);
        }
        habitacionRepository.deleteById(id);
    }
}
