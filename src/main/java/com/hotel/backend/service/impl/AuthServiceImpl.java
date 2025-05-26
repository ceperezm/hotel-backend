package com.hotel.backend.service.impl;

import com.hotel.backend.dto.auth.LoginRequest;
import com.hotel.backend.dto.auth.LoginResponse;
import com.hotel.backend.security.jwt.JwtUtil;
import com.hotel.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginResponse login(LoginRequest loginRequestDTO) {
        // Autenticar usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getNombreUsuario(),
                        loginRequestDTO.getPassword()
                )
        );

        // Establecer contexto de seguridad (IMPORTANTE - esto faltaba en tu código)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Extraer username del objeto Authentication y generar token
        String username = authentication.getName(); // Obtiene el email/username
        String token = jwtUtil.generateToken(username);

        // Retornar usando tu DTO actual
        return new LoginResponse(token);
    }

}
