package com.hotel.backend.listeners;

import com.hotel.backend.service.SyncQueueService;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EntityListener {

    private static SyncQueueService syncQueueService;

    @Autowired
    public void setSyncQueueService(SyncQueueService syncQueueService) {
        EntityListener.syncQueueService = syncQueueService;
    }

    @PostPersist
    public void postPersist(Object entity) {
        if (syncQueueService != null) {
            String entityName = entity.getClass().getSimpleName();
            Long entityId = getEntityId(entity);
            syncQueueService.queueForSync(entityName, entityId, "CREATE", entity);
        }
    }

    @PostUpdate
    public void postUpdate(Object entity) {
        if (syncQueueService != null) {
            String entityName = entity.getClass().getSimpleName();
            Long entityId = getEntityId(entity);
            syncQueueService.queueForSync(entityName, entityId, "UPDATE", entity);
        }
    }

    @PostRemove
    public void postRemove(Object entity) {
        if (syncQueueService != null) {
            String entityName = entity.getClass().getSimpleName();
            Long entityId = getEntityId(entity);
            syncQueueService.queueForSync(entityName, entityId, "DELETE", entity);
        }
    }

    private Long getEntityId(Object entity) {
        try {
            java.lang.reflect.Field idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            return (Long) idField.get(entity);
        } catch (Exception e) {
            log.error("Error obteniendo ID de entidad: {}", e.getMessage());
            return null;
        }
    }
}