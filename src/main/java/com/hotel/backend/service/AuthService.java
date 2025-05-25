package com.hotel.backend.service;

import com.hotel.backend.dto.auth.LoginRequest;
import com.hotel.backend.dto.auth.SignupRequest;
import com.hotel.backend.dto.usuario.UsuarioDTOResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    String login(LoginRequest loginRequest);
    UsuarioDTOResponse signup(SignupRequest signupRequest);
}
