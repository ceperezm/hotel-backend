package com.hotel.backend.controller;

import com.hotel.backend.service.CloudSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sync")
@RequiredArgsConstructor
public class SyncController {

    private final CloudSyncService cloudSyncService;

    @PostMapping("/manual")
    public ResponseEntity<String> forceSynchronization() {
        try {
            cloudSyncService.syncToCloud();
            cloudSyncService.syncFromCloud();
            return ResponseEntity.ok("Sincronización iniciada correctamente");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al iniciar sincronización: " + e.getMessage());
        }
    }
}