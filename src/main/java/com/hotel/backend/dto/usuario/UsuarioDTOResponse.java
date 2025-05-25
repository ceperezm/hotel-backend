package com.hotel.backend.dto.usuario;

import com.hotel.backend.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTOResponse {
    String nombreUsuario;
    ERole rol;
}
