package com.hotel.backend.dto.mapper;

import com.hotel.backend.dto.PagoDTO;
import com.hotel.backend.model.Pago;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PagoMapper {
    @Mapping(source = "factura.id", target = "facturaId")
    PagoDTO toDTO(Pago pago);

    @Mapping(target = "factura", ignore = true)
    Pago toEntity(PagoDTO pagoDTO);
}
