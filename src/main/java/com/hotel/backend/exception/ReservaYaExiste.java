package com.hotel.backend.exception;

import java.time.LocalDate;

public class ReservaYaExiste extends RuntimeException{
    public ReservaYaExiste(LocalDate fecha, Long idHorarioEspacio) {
        super("Ya existe una reserva con la fecha " + fecha + " e idHorarioEspacio " + idHorarioEspacio);
    }
}
