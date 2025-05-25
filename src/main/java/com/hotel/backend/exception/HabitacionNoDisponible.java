package com.hotel.backend.exception;

public class HabitacionNoDisponible extends RuntimeException{
    public HabitacionNoDisponible(String message) {
        super(message);
    }
}