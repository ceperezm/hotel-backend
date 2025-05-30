package com.hotel.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.backend.model.SyncQueue;
import com.hotel.backend.model.SyncStatus;
import com.hotel.backend.repository.local.SyncQueueRepository;
import com.hotel.backend.repository.local.SyncStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SyncQueueService {

    private final SyncQueueRepository syncQueueRepository;
    private final SyncStatusRepository syncStatusRepository;
    private final ObjectMapper objectMapper;

    // Configuración de reintentos
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 500;

    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 25,
            noRollbackFor = {CannotAcquireLockException.class})
    public void queueForSync(String entityName, Long entityId, String operation, Object entity) {
        int attempts = 0;
        boolean success = false;

        while (!success && attempts < MAX_RETRIES) {
            try {
                attempts++;
                log.debug("Intento {} de encolar para sincronización: {}", attempts, entityName);

                // Convertir entidad a JSON
                String entityJson = objectMapper.writeValueAsString(entity);

                SyncQueue queueItem = SyncQueue.builder()
                        .entityName(entityName)
                        .entityId(entityId)
                        .operation(operation)
                        .entityData(entityJson)
                        .createdAt(LocalDateTime.now())
                        .processed(false)
                        .retryCount(0)
                        .build();

                syncQueueRepository.save(queueItem);
                syncQueueRepository.flush();
                success = true;

            } catch (Exception e) {
                if (attempts >= MAX_RETRIES) {
                    log.error("Error al encolar para sincronización después de {} intentos: {}",
                            MAX_RETRIES, e.getMessage(), e);
                } else {
                    log.warn("Reintentando encolar (intento {}/{}): {}",
                            attempts, MAX_RETRIES, e.getMessage());
                    try {
                        Thread.sleep(RETRY_DELAY_MS * attempts); // Backoff exponencial
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        // Solo actualizar estado si se pudo guardar en la cola
        if (success) {
            try {
                updateSyncStatus(entityName);
            } catch (Exception e) {
                log.error("Error al actualizar estado después de encolar: {}", e.getMessage());
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 25,
            noRollbackFor = {CannotAcquireLockException.class})
    public void updateSyncStatus(String entityName) {
        int attempts = 0;
        boolean success = false;

        while (!success && attempts < MAX_RETRIES) {
            try {
                attempts++;
                log.debug("Intento {} de actualizar estado: {}", attempts, entityName);

                SyncStatus status = syncStatusRepository.findByTableName(entityName)
                        .orElse(null);

                if (status == null) {
                    status = new SyncStatus();
                    status.setTableName(entityName);
                    status.setLastLocalSync(LocalDateTime.now());
                    status.setLastCloudSync(LocalDateTime.now());
                    status.setLastSyncStatus("PENDING");
                    status.setPendingChanges(1);
                } else {
                    status.setPendingChanges(status.getPendingChanges() + 1);
                }

                syncStatusRepository.save(status);
                syncStatusRepository.flush();
                success = true;

            } catch (Exception e) {
                if (attempts >= MAX_RETRIES) {
                    log.error("Error al actualizar estado de sincronización después de {} intentos: {}",
                            MAX_RETRIES, e.getMessage(), e);
                } else {
                    log.warn("Reintentando actualizar estado (intento {}/{}): {}",
                            attempts, MAX_RETRIES, e.getMessage());
                    try {
                        Thread.sleep(RETRY_DELAY_MS * attempts);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}