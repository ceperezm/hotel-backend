package com.hotel.backend.dto.mapper;


import com.hotel.backend.dto.UsuarioDTORequest;
import com.hotel.backend.dto.UsuarioDTOResponse;
import com.hotel.backend.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rol", ignore = true) // Se setea en el servicio
    @Mapping(target = "reservaciones", ignore = true)
    Usuario toEntity(UsuarioDTORequest dto);

    @Mapping(source = "rol.nombre", target = "rol")
    UsuarioDTOResponse toDTO(Usuario usuario);
}
