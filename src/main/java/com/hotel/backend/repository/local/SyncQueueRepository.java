package com.hotel.backend.repository.local;

import com.hotel.backend.model.SyncQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SyncQueueRepository extends JpaRepository<SyncQueue, Long> {
    List<SyncQueue> findByProcessedOrderByCreatedAt(boolean processed);

    @Query("SELECT sq FROM SyncQueue sq WHERE sq.processed = false AND sq.retryCount < 5 ORDER BY sq.createdAt")
    List<SyncQueue> findPendingWithRetryLimit();

    List<SyncQueue> findByEntityNameAndEntityId(String entityName, Long entityId);
}