package com.hotel.backend.service.impl;

import com.hotel.backend.dto.auth.LoginRequest;
import com.hotel.backend.dto.auth.SignupRequest;
import com.hotel.backend.dto.usuario.UsuarioDTOResponse;
import com.hotel.backend.service.AuthService;

public class AuthServiceImpl implements AuthService {
    @Override
    public String login(LoginRequest loginRequest) {
        return "";
    }

    @Override
    public UsuarioDTOResponse signup(SignupRequest signupRequest) {
        return null;
    }
}
