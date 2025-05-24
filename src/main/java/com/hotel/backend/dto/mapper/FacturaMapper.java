package com.hotel.backend.dto.mapper;

import com.hotel.backend.dto.FacturaDTO;
import com.hotel.backend.model.Factura;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FacturaMapper {
    @Mapping(source = "reservacion.id", target = "reservacionId")
    FacturaDTO toDTO(Factura factura);

    @Mapping(target = "reservacion", ignore = true)
    @Mapping(target = "pagos", ignore = true)
    Factura toEntity(FacturaDTO facturaDTO);
}
