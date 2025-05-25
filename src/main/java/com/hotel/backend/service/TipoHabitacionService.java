package com.hotel.backend.service;

import com.hotel.backend.dto.TipoHabitacionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TipoHabitacionService {
    TipoHabitacionDTO crearTipo(TipoHabitacionDTO tipoHabitacionDTO);
    TipoHabitacionDTO actualizarTipo(Long id, TipoHabitacionDTO tipoHabitacionDTO);
    void eliminarTipo(Long id);
    List<TipoHabitacionDTO> listarTipos();

}
