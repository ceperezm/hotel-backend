package com.hotel.backend.dto.mapper;

import com.hotel.backend.dto.HabitacionDTO;
import com.hotel.backend.model.Habitacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HabitacionMapper {
    @Mapping(source = "tipoHabitacion.id", target = "tipoHabitacionId")
    @Mapping(source = "tipoHabitacion.nombre", target = "tipoHabitacionNombre")
    HabitacionDTO toDTO(Habitacion habitacion);

    @Mapping(target = "tipoHabitacion", ignore = true)
    @Mapping(target = "reservaciones", ignore = true) //campo en la entidad
    Habitacion toEntity(HabitacionDTO habitacionDTO);
}
