package com.hotel.backend.exception;

public class UsuarioYaExiste extends RuntimeException {
    public UsuarioYaExiste(String message) {
        super(message);
    }
}
