package com.hotel.backend.service;

import com.hotel.backend.dto.auth.LoginRequest;
import com.hotel.backend.dto.auth.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}
