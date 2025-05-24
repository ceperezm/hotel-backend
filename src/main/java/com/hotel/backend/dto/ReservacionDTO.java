package com.hotel.backend.dto;

import com.hotel.backend.model.emuns.EstadoReservacion;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservacionDTO {
    Long id;
    @NotNull
    LocalDateTime fechaReserva;
    @NotNull
    LocalDate fechaCheckin;
    @NotNull
    LocalDate fechaCheckout;

    @NotNull
    Integer numPersonas;

    String notas;

    @NotNull
    EstadoReservacion estadoReservacion;
    @NotNull
    Long habitacionId;
    @NotNull
    String numeroHabitacion;
    @NotNull
    Long huespedId;
    @NotNull
    Long usuarioId;
}
