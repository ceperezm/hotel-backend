package com.hotel.backend.dto.mapper;

import com.hotel.backend.dto.ReservacionDTO;
import com.hotel.backend.model.Reservacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservacionMapper {
    @Mapping(source = "habitacion.id", target = "habitacionId")
    @Mapping(source = "habitacion.numero", target = "numeroHabitacion")
    @Mapping(source = "huesped.id", target = "huespedId")
    @Mapping(source = "usuario.id", target = "usuarioId")
    ReservacionDTO toDTO(Reservacion reservacion);

    @Mapping(target = "habitacion", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "factura", ignore = true)
    Reservacion toEntity(ReservacionDTO reservacionDTO);
}
