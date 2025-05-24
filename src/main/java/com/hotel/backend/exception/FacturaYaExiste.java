package com.hotel.backend.exception;

public class FacturaYaExiste extends RuntimeException {
    public FacturaYaExiste(String message) {
        super(message);
    }
}
