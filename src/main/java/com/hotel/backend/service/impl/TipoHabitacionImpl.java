package com.hotel.backend.service.impl;
import com.hotel.backend.dto.TipoHabitacionDTO;
import com.hotel.backend.dto.mapper.TipoHabitacionMapper;
import com.hotel.backend.exception.HuespedYaExiste;
import com.hotel.backend.exception.ResourceNotFoundException;
import com.hotel.backend.exception.TipoHabitacionYaExiste;
import com.hotel.backend.model.Huesped;
import com.hotel.backend.model.TipoHabitacion;
import com.hotel.backend.repository.TipoHabitacionRepository;
import com.hotel.backend.service.TipoHabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoHabitacionImpl implements TipoHabitacionService {
    private final TipoHabitacionRepository tipoHabitacionRepository;
    private final TipoHabitacionMapper tipoHabitacionMapper;

    @Override
    public TipoHabitacionDTO crearTipo(TipoHabitacionDTO tipoHabitacionDTO) {
        //verificar si el tipo de habitacion ya existe
        if(tipoHabitacionRepository.findById(tipoHabitacionDTO.getId()).isPresent()) {
            throw new TipoHabitacionYaExiste("Tipo de habitación ya existe con id: " + tipoHabitacionDTO.getId());
        }
        // Mapear DTO a entidad para poder guardarlo
        TipoHabitacion tipoHabitacion = tipoHabitacionMapper.toEntity(tipoHabitacionDTO);
        // Guardar huesped y lo retorna como dto
        return tipoHabitacionMapper.toDTO(tipoHabitacionRepository.save(tipoHabitacion));
    }

    @Override
    public TipoHabitacionDTO actualizarTipo(Long id, TipoHabitacionDTO tipoHabitacionDTO) {
        TipoHabitacion existente = tipoHabitacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de habitación no encontrado con ID: " + id));

        existente.setNombre(tipoHabitacionDTO.getNombre());
        existente.setPrecioPorNoche(tipoHabitacionDTO.getPrecioPorNoche());
        existente.setCapacidad(tipoHabitacionDTO.getCapacidad());

        return tipoHabitacionMapper.toDTO(tipoHabitacionRepository.save(existente));
    }

    @Override
    public void eliminarTipo(Long id) {
        if(!tipoHabitacionRepository.existsById(id)){
            throw new ResourceNotFoundException("El tipo de Habitacion no encontrado con id: " + id);
        }
        tipoHabitacionRepository.deleteById(id);

    }

    @Override
    public List<TipoHabitacionDTO> listarTipos() {
        return tipoHabitacionRepository.findAll().stream()
                .map(tipoHabitacionMapper::toDTO)
                .toList();
    }
}
