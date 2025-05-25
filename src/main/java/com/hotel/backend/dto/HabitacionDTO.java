package com.hotel.backend.dto;

import com.hotel.backend.enums.EstadoHabitacion;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionDTO {
    Long id;
    @NotNull
    String numero;
    @NotNull
    EstadoHabitacion estado;
    @NotNull
    String descripcion;
    @NotNull
    String tipoHabitacionNombre;
    @NotNull
    Long tipoHabitacionId;
}
