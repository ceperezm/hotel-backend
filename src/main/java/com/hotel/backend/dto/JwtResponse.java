package com.hotel.backend.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String nombreUsuario;
    private String email;
    private List<String> roles;
}
