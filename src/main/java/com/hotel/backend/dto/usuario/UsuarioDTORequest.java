package com.hotel.backend.dto.usuario;

import com.hotel.backend.enums.ERole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTORequest {
    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    private String nombreUsuario;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotNull
    private String rol;
}
