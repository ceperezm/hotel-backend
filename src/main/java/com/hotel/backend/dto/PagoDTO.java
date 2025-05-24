package com.hotel.backend.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    Long id;
    @NotNull
    BigDecimal monto;
    @NotNull
    String metodo;
    @NotNull
    String referencia;
    @NotNull
    LocalDateTime fechaPago;
    @NotNull
    Long facturaId;
}
