package com.hotel.backend.service.impl;
import com.hotel.backend.dto.TipoHabitacionDTO;
import com.hotel.backend.dto.mapper.TipoHabitacionMapper;
import com.hotel.backend.exception.ResourceNotFoundException;
import com.hotel.backend.exception.TipoHabitacionYaExiste;
import com.hotel.backend.model.TipoHabitacion;
import com.hotel.backend.repository.local.TipoHabitacionRepository;
import com.hotel.backend.service.TipoHabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoHabitacionImpl implements TipoHabitacionService {
    private final TipoHabitacionRepository tipoHabitacionRepository;
    private final TipoHabitacionMapper tipoHabitacionMapper;

    @Override
    @Transactional(transactionManager = "localTransactionManager")
    public TipoHabitacionDTO crearTipo(TipoHabitacionDTO tipoHabitacionDTO) {
        // Validación por nombre
        if (tipoHabitacionRepository.findByNombre(tipoHabitacionDTO.getNombre()).isPresent()) {
            throw new TipoHabitacionYaExiste("Tipo de habitación ya existe con nombre: " + tipoHabitacionDTO.getNombre());
        }

        TipoHabitacion tipoHabitacion = tipoHabitacionMapper.toEntity(tipoHabitacionDTO);
        return tipoHabitacionMapper.toDTO(tipoHabitacionRepository.save(tipoHabitacion));
    }

    @Override
    @Transactional(transactionManager = "localTransactionManager")
    public TipoHabitacionDTO actualizarTipo(Long id, TipoHabitacionDTO tipoHabitacionDTO) {
        TipoHabitacion existente = tipoHabitacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de habitación no encontrado con ID: " + id));

        existente.setNombre(tipoHabitacionDTO.getNombre());
        existente.setPrecioPorNoche(tipoHabitacionDTO.getPrecioPorNoche());
        existente.setCapacidad(tipoHabitacionDTO.getCapacidad());

        return tipoHabitacionMapper.toDTO(tipoHabitacionRepository.save(existente));
    }

    @Override
    @Transactional(transactionManager = "localTransactionManager")
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
