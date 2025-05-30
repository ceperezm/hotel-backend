package com.hotel.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.backend.model.*;
import com.hotel.backend.repository.cloud.*;
import com.hotel.backend.repository.local.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
@RequiredArgsConstructor
public class CloudSyncService {

    private final SyncQueueRepository syncQueueRepository;
    private final SyncStatusRepository syncStatusRepository;
    private final ObjectMapper objectMapper;
    private final NetworkMonitorService networkMonitorService;
    private final ApplicationContext context;

    private final Map<String, RepositoryPair> repositoryMap = new HashMap<>();
    private final ReentrantLock syncLock = new ReentrantLock(); // Bloqueo para sincronización

    // Inicializa el mapa de repositorios
    public void initRepositoryMap() {
        repositoryMap.put("Usuario", new RepositoryPair(
                context.getBean(UsuarioRepository.class),
                context.getBean(UsuarioRepositoryCloud.class)));
        repositoryMap.put("Huesped", new RepositoryPair(
                context.getBean(HuespedRepository.class),
                context.getBean(HuespedRepositoryCloud.class)));
        repositoryMap.put("Habitacion", new RepositoryPair(
                context.getBean(HabitacionRepository.class),
                context.getBean(HabitacionRepositoryCloud.class)));
        repositoryMap.put("Reserva", new RepositoryPair(
                context.getBean(ReservaRepository.class),
                context.getBean(ReservaRepositoryCoud.class))); // Corregido
        repositoryMap.put("Factura", new RepositoryPair(
                context.getBean(FacturaRepository.class),
                context.getBean(FacturaRepositoryCloud.class)));
        repositoryMap.put("Pago", new RepositoryPair(
                context.getBean(PagoRepository.class),
                context.getBean(PagoRepositoryCloud.class)));
        repositoryMap.put("TipoHabitacion", new RepositoryPair(
                context.getBean(TipoHabitacionRepository.class),
                context.getBean(TipoHabitacionRepositoryCloud.class)));
        repositoryMap.put("Rol", new RepositoryPair(
                context.getBean(RolRepository.class),
                context.getBean(RolRepositoryCloud.class)));
    }

    @Scheduled(fixedRate = 60000) // Cada minuto
    public void syncToCloud() {
        if (!networkMonitorService.isNetworkAvailable()) {
            log.debug("Red no disponible, sincronización pospuesta");
            return;
        }

        if (syncLock.tryLock()) {
            try {
                log.info("Iniciando sincronización local -> nube");
                List<SyncQueue> pendingItems = syncQueueRepository.findPendingWithRetryLimit();

                for (SyncQueue item : pendingItems) {
                    try {
                        processQueueItem(item);
                    } catch (Exception e) {
                        handleSyncError(item, e);
                    }
                }
            } finally {
                syncLock.unlock();
            }
        } else {
            log.warn("Otra sincronización está en progreso. Operación pospuesta.");
        }
    }

    @Transactional
    public void processQueueItem(SyncQueue item) throws Exception { // Cambiado a público
        RepositoryPair repositories = repositoryMap.get(item.getEntityName());
        if (repositories == null) {
            log.error("No se encontró repositorio para la entidad: {}", item.getEntityName());
            markAsProcessed(item, "Repositorio no encontrado");
            return;
        }

        switch (item.getOperation()) {
            case "CREATE":
            case "UPDATE":
                Object entity = objectMapper.readValue(item.getEntityData(),
                        Class.forName("com.hotel.backend.model." + item.getEntityName()));
                ((JpaRepository) repositories.cloudRepo).save(entity);
                break;
            case "DELETE":
                ((JpaRepository) repositories.cloudRepo).deleteById(item.getEntityId());
                break;
        }

        markAsProcessed(item, null);
        updateSyncStatus(item.getEntityName());
    }

    private void markAsProcessed(SyncQueue item, String errorMessage) {
        item.setProcessed(errorMessage == null);
        item.setProcessedAt(LocalDateTime.now());
        item.setRetryCount(item.getRetryCount() + 1);
        item.setErrorMessage(errorMessage);
        syncQueueRepository.save(item);
    }

    private void handleSyncError(SyncQueue item, Exception e) {
        log.error("Error al sincronizar {}: {}", item.getEntityName(), e.getMessage(), e);
        item.setRetryCount(item.getRetryCount() + 1);
        item.setErrorMessage(e.getMessage());
        syncQueueRepository.save(item);
    }

    @Scheduled(fixedRate = 60000) // Cada 5 minutos
    public void syncFromCloud() {
        if (!networkMonitorService.isNetworkAvailable()) {
            log.debug("Red no disponible, sincronización nube -> local pospuesta");
            return;
        }

        if (syncLock.tryLock()) {
            try {
                log.info("Iniciando sincronización nube -> local");

                for (Map.Entry<String, RepositoryPair> entry : repositoryMap.entrySet()) {
                    try {
                        syncEntityFromCloud(entry.getKey(), entry.getValue());
                    } catch (Exception e) {
                        log.error("Error sincronizando {} desde la nube: {}", entry.getKey(), e.getMessage(), e);
                    }
                }
            } finally {
                syncLock.unlock();
            }
        } else {
            log.warn("Otra sincronización está en progreso. Operación pospuesta.");
        }
    }

    @Transactional
    public void syncEntityFromCloud(String entityName, RepositoryPair repos) { // Cambiado a público
        Optional<SyncStatus> statusOpt = syncStatusRepository.findByTableName(entityName);

        LocalDateTime lastSync = statusOpt.map(SyncStatus::getLastCloudSync)
                .orElse(LocalDateTime.now().minusDays(30));

        List<?> cloudEntities = ((JpaRepository) repos.cloudRepo).findAll();

        for (Object cloudEntity : cloudEntities) {
            try {
                ((JpaRepository) repos.localRepo).save(cloudEntity);
            } catch (Exception e) {
                log.error("Error al guardar entidad {} localmente: {}", entityName, e.getMessage());
            }
        }

        SyncStatus status = statusOpt.orElse(new SyncStatus());
        LocalDateTime now = LocalDateTime.now();

        status.setTableName(entityName);
        status.setLastCloudSync(LocalDateTime.now());
        status.setLastLocalSync(now);
        status.setLastSyncStatus("SUCCESS");
        status.setPendingChanges(0);
        syncStatusRepository.save(status);
    }

    private void updateSyncStatus(String entityName) {
        SyncStatus status = syncStatusRepository.findByTableName(entityName)
                .orElse(new SyncStatus());

        status.setTableName(entityName);
        status.setLastLocalSync(LocalDateTime.now());
        status.setLastSyncStatus("SUCCESS");

        long pendingCount = syncQueueRepository.findByProcessedOrderByCreatedAt(false).size();
        status.setPendingChanges((int) pendingCount);

        syncStatusRepository.save(status);
    }

    private static class RepositoryPair {
        final Object localRepo;
        final Object cloudRepo;

        RepositoryPair(Object localRepo, Object cloudRepo) {
            this.localRepo = localRepo;
            this.cloudRepo = cloudRepo;
        }
    }
}