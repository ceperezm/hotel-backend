package com.hotel.backend.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoHabitacionDTO {
    Long id;
    @NotNull
    String nombre;
    @NotNull
    BigDecimal precioPorNoche;
    @NotNull
    Integer capacidad;
}
