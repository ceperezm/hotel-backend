package com.hotel.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HuespedDTO {
    Long id;

    @NotNull
    String nombre;

    @NotNull
    String apellido;

    @NotNull
    String email;

    @NotNull
    String telefono;

    @NotNull
    String direccion;

    @NotNull
    String ciudad;

    @NotNull
    String pais;

    @NotNull
    String documentoIdentidad;

    @NotNull
    String tipoDocumento;

    String notas;
}
