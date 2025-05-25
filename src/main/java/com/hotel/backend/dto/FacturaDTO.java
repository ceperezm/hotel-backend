package com.hotel.backend.dto;

import com.hotel.backend.enums.EstadoPago;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDTO {
    Long id;
    @NotNull
    String numeroFactura;
    @NotNull
    BigDecimal total;
    @NotNull
    LocalDate fechaEmision;
    @NotNull
    LocalDate fechaVencimiento;
    @NotNull
    EstadoPago estadoPago;
    @NotNull
    Long reservaId;
}
