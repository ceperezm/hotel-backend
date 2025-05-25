package com.hotel.backend.exception;

public class TipoHabitacionYaExiste extends RuntimeException {
    public TipoHabitacionYaExiste(String message) {
        super(message);
    }
}
