package com.hotel.backend.dto.mapper;

import com.hotel.backend.dto.TipoHabitacionDTO;
import com.hotel.backend.model.TipoHabitacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TipoHabitacionMapper {
    TipoHabitacionDTO toDTO(TipoHabitacion tipoHabitacion);

    @Mapping(target = "habitaciones", ignore = true)
    TipoHabitacion toEntity(TipoHabitacionDTO tipoHabitacionDTO);
}
