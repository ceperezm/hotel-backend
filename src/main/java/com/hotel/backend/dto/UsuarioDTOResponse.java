package com.hotel.backend.dto;

import com.hotel.backend.model.emuns.ERole;
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
