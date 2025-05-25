package com.hotel.backend.dto.mapper;

import com.hotel.backend.dto.FacturaDTO;
import com.hotel.backend.model.Factura;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FacturaMapper {
    @Mapping(source = "reserva.id", target = "reservaId")
    FacturaDTO toDTO(Factura factura);

    @Mapping(target = "reserva", ignore = true)
    @Mapping(target = "pagos", ignore = true)
    Factura toEntity(FacturaDTO facturaDTO);
}
