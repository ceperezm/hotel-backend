package com.hotel.backend.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //maneja cuando no encuentra un recurso
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .errors(null)
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // Maneja las excepciones IllegalArgumentException, que suelen lanzarse cuando un argumento proporcionado no es válido.
    // Devuelve un error 400 (BAD REQUEST) con el mensaje de la excepción.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .errors(null)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    // Maneja cualquier excepción genérica que no haya sido capturada por los manejadores específicos.
    // Devuelve un error 500 (INTERNAL SERVER ERROR) con un mensaje genérico.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred")
                .errors(null)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //maneja cuando el usuario ya existe
    @ExceptionHandler(UsuarioYaExiste.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(UsuarioYaExiste userAlreadyExists) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .message(userAlreadyExists.getMessage())
                .errors(null)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    //Maneja cuando la factura ya existe
    @ExceptionHandler(FacturaYaExiste.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(FacturaYaExiste facturaAlreadyExists) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .message(facturaAlreadyExists.getMessage())
                .errors(null)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    //Maneja cuando la reserva ya existe
    @ExceptionHandler(ReservaYaExiste.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(ReservaYaExiste reservaAlreadyExists) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .message(reservaAlreadyExists.getMessage())
                .errors(null)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    //Maneja cuando el huesped ya existe
    @ExceptionHandler(HuespedYaExiste.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(HuespedYaExiste huespedAlreadyExists) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .message(huespedAlreadyExists.getMessage())
                .errors(null)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    //Maneja cuando el tipo de habitacion ya existe
    @ExceptionHandler(TipoHabitacionYaExiste.class)
    public ResponseEntity<ApiError> handleUserAlreadyExists(TipoHabitacionYaExiste tipoHabitacionYaExiste) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .message(tipoHabitacionYaExiste.getMessage())
                .errors(null)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    //Maneja cuando la habitacion ocupadas
    @ExceptionHandler(HabitacionNoDisponible.class)
    public ResponseEntity<ApiError> handleRoomNoAvailable(HabitacionNoDisponible habitacionNoDisponible) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .message(habitacionNoDisponible.getMessage())
                .errors(null)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    //Maneja cuando la el checkin falla
    @ExceptionHandler(ProblemaRealizarCheckIn.class)
    public ResponseEntity<ApiError> handlerProblemaEstadoReserva(ProblemaRealizarCheckIn ex){
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .errors(null)
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }


}
