package com.hotel.backend.exception;

public class ReservaYaExiste extends RuntimeException{
    public ReservaYaExiste(String message) {
        super(message);
    }
}
