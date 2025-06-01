package com.hotel.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiRootController {
    @GetMapping("/test")
    public Map<String, String> root() {
        return Map.of("message", "Backend conectado correctamente");
    }
}
