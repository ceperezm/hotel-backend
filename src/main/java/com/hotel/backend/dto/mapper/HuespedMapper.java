package com.hotel.backend.dto.mapper;

import com.hotel.backend.dto.HuespedDTO;
import com.hotel.backend.model.Huesped;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HuespedMapper {
    HuespedDTO toDTO(Huesped huesped);

    @Mapping(target = "reservaciones", ignore = true)
    Huesped toEntity(HuespedDTO huespedDTO);
}
