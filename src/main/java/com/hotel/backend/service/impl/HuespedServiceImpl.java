package com.hotel.backend.service.impl;

import com.hotel.backend.dto.HuespedDTO;
import com.hotel.backend.dto.mapper.HuespedMapper;
import com.hotel.backend.exception.HuespedYaExiste;
import com.hotel.backend.exception.ResourceNotFoundException;
import com.hotel.backend.model.Huesped;
import com.hotel.backend.repository.local.HuespedRepository;
import com.hotel.backend.service.HuespedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HuespedServiceImpl implements HuespedService {
    private final HuespedRepository huespedRepository;
    private final HuespedMapper huespedMapper;

    @Override
    @Transactional(transactionManager = "localTransactionManager")
    public HuespedDTO registrarHuesped(HuespedDTO huespedDTO) {
        //verificar si el huesped ya existe
        if(huespedRepository.findByDocumentoIdentidad(huespedDTO.getDocumentoIdentidad()).isPresent()) {
            throw new HuespedYaExiste("Huesped ya existe con ese numero de identificacion: " + huespedDTO.getDocumentoIdentidad());
        }
        // Mapear DTO a entidad para poder guardarlo
        Huesped huesped = huespedMapper.toEntity(huespedDTO);
        // Guardar huesped y lo retorna como dto
        return huespedMapper.toDTO(huespedRepository.save(huesped));
    }

    @Override
    @Transactional(transactionManager = "localTransactionManager")
    public HuespedDTO actualizarHuesped(Long id, HuespedDTO huespedDTO) {
        Huesped existente = huespedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Huésped no encontrado con ID: " + id));

        existente.setNombre(huespedDTO.getNombre());
        existente.setApellido(huespedDTO.getApellido());
        existente.setEmail(huespedDTO.getEmail());
        existente.setTelefono(huespedDTO.getTelefono());
        existente.setDireccion(huespedDTO.getDireccion());
        existente.setCiudad(huespedDTO.getCiudad());
        existente.setPais(huespedDTO.getPais());
        existente.setDocumentoIdentidad(huespedDTO.getDocumentoIdentidad());
        existente.setTipoDocumento(huespedDTO.getTipoDocumento());
        existente.setNotas(huespedDTO.getNotas());

        return huespedMapper.toDTO(huespedRepository.save(existente));
    }

    @Override
    @Transactional(transactionManager = "localTransactionManager")
    public void eliminarHuesped(Long id) {
        if(!huespedRepository.existsById(id)) {
            throw new ResourceNotFoundException("Huesped no encontrado con ID: " + id);
        }
        huespedRepository.deleteById(id);
    }

    @Override
    public HuespedDTO obtenerHuespedPorId(Long id) {
        return huespedRepository.findById(id)
                .map(huespedMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Huesped no encontrado con id: " + id));
    }

    @Override
    public List<HuespedDTO> listarHuespedes() {
        return huespedRepository.findAll().stream()
                .map(huespedMapper::toDTO)
                .toList();
    }
}
