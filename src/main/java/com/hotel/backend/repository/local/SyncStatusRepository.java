package com.hotel.backend.repository.local;

import com.hotel.backend.model.SyncStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SyncStatusRepository extends JpaRepository<SyncStatus, Long> {
    Optional<SyncStatus> findByTableName(String tableName);
}